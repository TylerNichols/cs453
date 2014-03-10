/* This file was generated by SableCC (http://www.sablecc.org/).  
 * Then modified.
 */
package ast.node;

import java.util.*;
import ast.visitor.*;

@SuppressWarnings("nls")
public final class BlockStatement extends IStatement
{
    private final LinkedList<IStatement> _statements_ = new LinkedList<IStatement>();

    public BlockStatement(int _line_, int _pos_, List<IStatement> _statements_)
    {
        super(_line_, _pos_);
        
        setStatements(_statements_);

    }

    public BlockStatement(int _line_, int _pos_)
    {
        super(_line_, _pos_);
    }
    
    @Override
    public int getNumExpChildren() { return 0; }
    
    @Override
    public Object clone()
    {
        return new BlockStatement(
                this.getLine(),
                this.getPos(),
                cloneList(this._statements_));
    }

    public void accept(Visitor v)
    {
        v.visitBlockStatement(this);
    }

    public LinkedList<IStatement> getStatements()
    {
        return this._statements_;
    }

    public void setStatements(List<IStatement> list)
    {
        this._statements_.clear();
        this._statements_.addAll(list);
        for(IStatement e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    @Override
    void removeChild(Node child)
    {
        // Remove child
        if(this._statements_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        // Replace child
        for(ListIterator<IStatement> i = this._statements_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((IStatement) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
