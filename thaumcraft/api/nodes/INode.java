package thaumcraft.api.nodes;

import thaumcraft.api.aspects.*;

public interface INode extends IAspectContainer
{
    String getId();
    
    AspectList getAspectsBase();
    
    NodeType getNodeType();
    
    void setNodeType(final NodeType p0);
    
    void setNodeModifier(final NodeModifier p0);
    
    NodeModifier getNodeModifier();
    
    int getNodeVisBase(final Aspect p0);
    
    void setNodeVisBase(final Aspect p0, final short p1);
}
