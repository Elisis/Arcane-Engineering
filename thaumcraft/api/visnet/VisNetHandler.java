package thaumcraft.api.visnet;

import java.lang.ref.*;
import net.minecraft.world.*;
import thaumcraft.api.aspects.*;
import java.util.*;
import net.minecraft.tileentity.*;
import thaumcraft.api.*;
import net.minecraft.util.*;

public class VisNetHandler
{
    public static HashMap<Integer, HashMap<WorldCoordinates, WeakReference<TileVisNode>>> sources;
    static ArrayList<WorldCoordinates> cache;
    private static HashMap<WorldCoordinates, ArrayList<WeakReference<TileVisNode>>> nearbyNodes;
    
    public static int drainVis(final World world, final int x, final int y, final int z, final Aspect aspect, int amount) {
        int drainedAmount = 0;
        final WorldCoordinates drainer = new WorldCoordinates(x, y, z, world.provider.dimensionId);
        if (!VisNetHandler.nearbyNodes.containsKey(drainer)) {
            calculateNearbyNodes(world, x, y, z);
        }
        final ArrayList<WeakReference<TileVisNode>> nodes = VisNetHandler.nearbyNodes.get(drainer);
        if (nodes != null && nodes.size() > 0) {
            for (final WeakReference<TileVisNode> noderef : nodes) {
                final TileVisNode node = noderef.get();
                if (node == null) {
                    continue;
                }
                final int a = node.consumeVis(aspect, amount);
                drainedAmount += a;
                amount -= a;
                if (a > 0) {
                    final int color = Aspect.getPrimalAspects().indexOf(aspect);
                    generateVisEffect(world.provider.dimensionId, x, y, z, node.xCoord, node.yCoord, node.zCoord, color);
                }
                if (amount <= 0) {
                    break;
                }
            }
        }
        return drainedAmount;
    }
    
    public static void generateVisEffect(final int dim, final int x, final int y, final int z, final int x2, final int y2, final int z2, final int color) {
        ThaumcraftApi.internalMethods.generateVisEffect(dim, x, y, z, x2, y2, z2, color);
    }
    
    public static void addSource(final World world, final TileVisNode vs) {
        HashMap<WorldCoordinates, WeakReference<TileVisNode>> sourcelist = VisNetHandler.sources.get(world.provider.dimensionId);
        if (sourcelist == null) {
            sourcelist = new HashMap<WorldCoordinates, WeakReference<TileVisNode>>();
        }
        sourcelist.put(vs.getLocation(), new WeakReference<TileVisNode>(vs));
        VisNetHandler.sources.put(world.provider.dimensionId, sourcelist);
        VisNetHandler.nearbyNodes.clear();
    }
    
    public static boolean isNodeValid(final WeakReference<TileVisNode> node) {
        return node != null && node.get() != null && !node.get().isInvalid();
    }
    
    public static WeakReference<TileVisNode> addNode(final World world, final TileVisNode vn) {
        final WeakReference ref = new WeakReference((T)vn);
        HashMap<WorldCoordinates, WeakReference<TileVisNode>> sourcelist = VisNetHandler.sources.get(world.provider.dimensionId);
        if (sourcelist == null) {
            sourcelist = new HashMap<WorldCoordinates, WeakReference<TileVisNode>>();
            return null;
        }
        ArrayList<Object[]> nearby = new ArrayList<Object[]>();
        for (final WeakReference<TileVisNode> root : sourcelist.values()) {
            if (!isNodeValid(root)) {
                continue;
            }
            final TileVisNode source = root.get();
            final float r = inRange(world, vn.getLocation(), source.getLocation(), vn.getRange());
            if (r > 0.0f) {
                nearby.add(new Object[] { source, r - vn.getRange() * 2 });
            }
            nearby = findClosestNodes(vn, source, nearby);
            VisNetHandler.cache.clear();
        }
        float dist = Float.MAX_VALUE;
        TileVisNode closest = null;
        if (nearby.size() > 0) {
            for (final Object[] o : nearby) {
                if ((float)o[1] < dist && (vn.getAttunement() == -1 || ((TileVisNode)o[0]).getAttunement() == -1 || vn.getAttunement() == ((TileVisNode)o[0]).getAttunement()) && canNodeBeSeen(vn, (TileVisNode)o[0])) {
                    dist = (float)o[1];
                    closest = (TileVisNode)o[0];
                }
            }
        }
        if (closest != null) {
            closest.getChildren().add(ref);
            VisNetHandler.nearbyNodes.clear();
            return new WeakReference<TileVisNode>(closest);
        }
        return null;
    }
    
    public static ArrayList<Object[]> findClosestNodes(final TileVisNode target, final TileVisNode parent, ArrayList<Object[]> in) {
        if (VisNetHandler.cache.size() > 512 || VisNetHandler.cache.contains(new WorldCoordinates(parent))) {
            return in;
        }
        VisNetHandler.cache.add(new WorldCoordinates(parent));
        for (final WeakReference<TileVisNode> childWR : parent.getChildren()) {
            final TileVisNode child = childWR.get();
            if (child != null && !child.equals(target) && !child.equals(parent)) {
                final float r2 = inRange(child.getWorld(), child.getLocation(), target.getLocation(), target.getRange());
                if (r2 > 0.0f) {
                    in.add(new Object[] { child, r2 });
                }
                in = findClosestNodes(target, child, in);
            }
        }
        return in;
    }
    
    private static float inRange(final World world, final WorldCoordinates cc1, final WorldCoordinates cc2, final int range) {
        final float distance = cc1.getDistanceSquaredToWorldCoordinates(cc2);
        return (distance > range * range) ? -1.0f : distance;
    }
    
    private static void calculateNearbyNodes(final World world, final int x, final int y, final int z) {
        HashMap<WorldCoordinates, WeakReference<TileVisNode>> sourcelist = VisNetHandler.sources.get(world.provider.dimensionId);
        if (sourcelist == null) {
            sourcelist = new HashMap<WorldCoordinates, WeakReference<TileVisNode>>();
            return;
        }
        final ArrayList<WeakReference<TileVisNode>> cn = new ArrayList<WeakReference<TileVisNode>>();
        final WorldCoordinates drainer = new WorldCoordinates(x, y, z, world.provider.dimensionId);
        final ArrayList<Object[]> nearby = new ArrayList<Object[]>();
        for (final WeakReference<TileVisNode> root : sourcelist.values()) {
            if (!isNodeValid(root)) {
                continue;
            }
            final TileVisNode source = root.get();
            TileVisNode closest = null;
            float range = Float.MAX_VALUE;
            final float r = inRange(world, drainer, source.getLocation(), source.getRange());
            if (r > 0.0f) {
                range = r;
                closest = source;
            }
            ArrayList<WeakReference<TileVisNode>> children = new ArrayList<WeakReference<TileVisNode>>();
            children = getAllChildren(source, children);
            for (final WeakReference<TileVisNode> child : children) {
                final TileVisNode n = child.get();
                if (n != null && !n.equals(root)) {
                    final float r2 = inRange(n.getWorld(), n.getLocation(), drainer, n.getRange());
                    if (r2 <= 0.0f || r2 >= range) {
                        continue;
                    }
                    range = r2;
                    closest = n;
                }
            }
            if (closest == null) {
                continue;
            }
            cn.add(new WeakReference<TileVisNode>(closest));
        }
        VisNetHandler.nearbyNodes.put(drainer, cn);
    }
    
    private static ArrayList<WeakReference<TileVisNode>> getAllChildren(final TileVisNode source, ArrayList<WeakReference<TileVisNode>> list) {
        for (final WeakReference<TileVisNode> child : source.getChildren()) {
            final TileVisNode n = child.get();
            if (n != null && n.getWorld() != null && isChunkLoaded(n.getWorld(), n.xCoord, n.zCoord)) {
                list.add(child);
                list = getAllChildren(n, list);
            }
        }
        return list;
    }
    
    public static boolean isChunkLoaded(final World world, final int x, final int z) {
        final int xx = x >> 4;
        final int zz = z >> 4;
        return world.getChunkProvider().chunkExists(xx, zz);
    }
    
    public static boolean canNodeBeSeen(final TileVisNode source, final TileVisNode target) {
        final MovingObjectPosition mop = ThaumcraftApiHelper.rayTraceIgnoringSource(source.getWorld(), Vec3.createVectorHelper(source.xCoord + 0.5, source.yCoord + 0.5, source.zCoord + 0.5), Vec3.createVectorHelper(target.xCoord + 0.5, target.yCoord + 0.5, target.zCoord + 0.5), false, true, false);
        return mop == null || (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mop.blockX == target.xCoord && mop.blockY == target.yCoord && mop.blockZ == target.zCoord);
    }
    
    static {
        VisNetHandler.sources = new HashMap<Integer, HashMap<WorldCoordinates, WeakReference<TileVisNode>>>();
        VisNetHandler.cache = new ArrayList<WorldCoordinates>();
        VisNetHandler.nearbyNodes = new HashMap<WorldCoordinates, ArrayList<WeakReference<TileVisNode>>>();
    }
}
