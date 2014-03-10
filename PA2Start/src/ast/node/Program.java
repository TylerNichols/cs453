/* This file was generated by SableCC (http://www.sablecc.org/). 
 * Then modified.
 */

package ast.node;

import ast.visitor.*;
import java.util.*;

@SuppressWarnings("nls")
public final class Program extends Node
{
    private MainClass _mainClass_;
    private final LinkedList<IClassDecl> _classDecls_ = new LinkedList<IClassDecl>();

    public Program(int _line_, int _pos_, 
            MainClass _mainClass_, List<IClassDecl> _classDecls_)
    {
        super(_line_, _pos_);
        
        setMainClass(_mainClass_);

        setClassDecls(_classDecls_);

    }

    public Program(int _line_, int _pos_, 
            MainClass _mainClass_)
    {
        super(_line_, _pos_);
        
        setMainClass(_mainClass_);

    }

    @Override
    public int getNumExpChildren() { return 0; }
    
    @Override
    public Object clone()
    {
        return new Program(
                this.getLine(),
                this.getPos(),
                cloneNode(this._mainClass_),
                cloneList(this._classDecls_));
    }

    public void accept(Visitor v)
    {
        v.visitProgram(this);
    }

    public MainClass getMainClass()
    {
        return this._mainClass_;
    }

    public void setMainClass(MainClass node)
    {
        if(this._mainClass_ != null)
        {
            this._mainClass_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._mainClass_ = node;
    }

    public LinkedList<IClassDecl> getClassDecls()
    {
        return this._classDecls_;
    }

    public void setClassDecls(List<IClassDecl> list)
    {
        this._classDecls_.clear();
        this._classDecls_.addAll(list);
        for(IClassDecl e : list)
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
        if(this._mainClass_ == child)
        {
            this._mainClass_ = null;
            return;
        }

        if(this._classDecls_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        // Replace child
        if(this._mainClass_ == oldChild)
        {
            setMainClass((MainClass) newChild);
            return;
        }

        for(ListIterator<IClassDecl> i = this._classDecls_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((IClassDecl) newChild);
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
