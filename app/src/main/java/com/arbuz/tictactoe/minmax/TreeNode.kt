package com.arbuz.tictactoe.minmax

public class TreeNode (array: Array<Array<Byte?>>,
                       player: Player?,
                       numberCell: IntArray?,
                       dimension: Int,
                       alpha: Double?,
                       beta: Double?,
                       typeAssessment: TypeAssessment = TypeAssessment.Maximization,
                       isRoot: Boolean = false)
{
    public var IsRoot: Boolean = isRoot
    public var Parent : TreeNode = this
    public var Dimension: Int = dimension
    public var IsUsed: Boolean = player != null
    public var Alpha : Double? = alpha
    public var Beta : Double? = beta
    public var NumberCell:  IntArray? = numberCell
    public var GameArray : Array<Array<Byte?>> = array
    public var Level: Int = 0
    public var Assessment : Enum<TypeAssessment> = typeAssessment
    public var Children: MutableCollection<TreeNode>  = mutableListOf()
    public var ThatUsedPalyer: Player? = player
    public var AllPosition: MutableList<IntArray>  = GetAllPosition()
    public var PruningLocal: MutableList<IntArray>  = mutableListOf()

    fun GetLevel(treeNode: TreeNode): Int {
        if(treeNode.IsRoot) {
            return 1
        }
        else{
            return treeNode.Level + 1
        }
    }

    fun AddChild(array: Array<Array<Byte?>>,
                 player: Player,
                 numberCell: IntArray,
                 dimension: Int,
                 alpha: Double?,
                 beta: Double?): TreeNode
    {
        var child: TreeNode = TreeNode(array, player, numberCell, dimension, alpha, beta)

        Children?.add(child)

        child.Assessment = GetTypeAssessment(this.Assessment )
        child.Level = GetLevel(this)
        child.Parent = this

        return child
    }

    fun GetTypeAssessment(thisAssessment: Enum<TypeAssessment>) : TypeAssessment
    {
        if(thisAssessment == TypeAssessment.Maximization ){
            return TypeAssessment.Minimization
        }

        return TypeAssessment.Maximization
    }

    fun DeleteChild(child: TreeNode): Boolean {
        var isDeleteChild: Boolean = child.Parent.Children.remove(child)

        return isDeleteChild
    }

    fun GetPosition() : MutableList<IntArray>?{
        var list: MutableList<IntArray>  = mutableListOf()

        var all = GetFreePosition()

        for(i:Int in 0..all.count()-1) {
            var pos = all[i]
            if(PruningLocal.filter { pos[0] == it[0] && pos[1] == it[1] }.count() == 0){
                list.add(pos)
            }
        }

        return list
    }

    fun GetFreePosition() : MutableList<IntArray>
    {
        var list: MutableList<IntArray>  = mutableListOf()

        var all = GetAllPosition()
        var children = this.Children.map { it.NumberCell }

        for(i:Int in 0..all.count()-1) {
            var pos = all[i]
            if(children.filter { pos[0] == it!![0] && pos[1] == it!![1] }.count() == 0){
                list.add(pos)
            }
        }

        return list
    }

    fun GetAllPosition() : MutableList<IntArray>
    {
        var list: MutableList<IntArray>  = mutableListOf()

        for(i:Int in 0..Dimension-1)
        {
            for(j:Int in 0..Dimension-1)
            {
                if(GameArray[i][j] == null)
                {
                    var cell: IntArray = IntArray(2, {0})
                    cell[0] = i
                    cell[1] = j
                    list.add(cell)
                }
            }
        }

        return list
    }
}