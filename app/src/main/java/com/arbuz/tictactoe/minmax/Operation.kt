package com.arbuz.tictactoe.minmax

public class Operation( array: Array<Array<Byte?>>,
                        figureLenght: Int,
                        deth: Int,){

    val FigureLenght: Int = figureLenght
    val Dimension: Int = array.size
    val GameArray: Array<Array<Byte?>> = array.clone()
    val Deth: Int = deth
    var Graph: TreeNode =
        TreeNode(array,
            null,
            null,
            Dimension,
            Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            TypeAssessment.Maximization,
            true)
    val UsedPositionCount: Int = (array.size * array.size) - listOf(Graph.AllPosition).size

    val helpers: Helpers = Helpers()
    val heuristics: Heuristics = Heuristics(Dimension, FigureLenght)

    val FreePositionCount: Int = helpers.GetCountFreePosition(array)
    val DethGenerate: Int = if(deth == 0) FreePositionCount else Deth

    /* Minimax Alpha Beta Pruning (Alpha > Beta)*/
    public fun GetEstimatedGraph(): TreeNode
    {
        while (true)
        {
            var gameArray: Array<Array<Byte?>> = Graph.GameArray
            var player: Player = helpers.SwithcPlayer(gameArray)
            var free: kotlin.collections.List<IntArray>?  = Graph.GetPosition()

            if (free != null) {

                if(free.size > 0 && Graph.Level != DethGenerate) {

                    var position: IntArray = free.first()
                    var arrayNext: Array<Array<Byte?>> = helpers.CopyAndCreateArray(gameArray)
                    arrayNext[position[0]][position[1]] = player.NumericAttributePlayer

                    if(Graph.Level + 1 == DethGenerate) {

                        var rating:Double =  heuristics.Analysis(arrayNext, player, position)

                        if (Graph.Alpha!! > Graph.Beta!!) {
                            Graph.PruningLocal.add(position)
                            continue
                        }

                        var child: TreeNode = Graph.AddChild(arrayNext, player, position, Dimension,rating, rating )
                        Graph = child
                        continue
                    }
                    else {
                        if (Graph.Alpha!! > Graph.Beta!!) {
                            Graph.PruningLocal.add(position)
                            continue
                        }

                        var child: TreeNode = Graph.AddChild(arrayNext, player, position, Dimension, Graph.Alpha, Graph.Beta )
                        Graph = child
                        continue
                    }
                }
                else
                {
                    if(Graph.IsRoot && free.size == 0){
                        break
                    }
                    else if (!Graph.IsRoot && Graph.Parent.IsRoot){

                        if (Graph.Parent.Alpha!! > Graph.Beta!!) {
                            var position: IntArray = Graph.NumberCell!!
                            Graph.Parent.PruningLocal.add(position)
                            Graph.Parent.DeleteChild(Graph)
                        }
                    }

                    Graph = Graph.Parent

                    if(Graph.Assessment == TypeAssessment.Maximization) {
                        var maxBeta: Double? = Graph.Children.toTypedArray().maxByOrNull { it -> it.Beta!! }?.Beta
                        Graph.Alpha = maxBeta
                        continue
                    }
                    else{
                        var minAlpha: Double? = Graph.Children.toTypedArray().minByOrNull{ it -> it.Alpha!! }?.Alpha
                        Graph.Beta = minAlpha
                        continue
                    }
                }
            }
        }

        return  Graph
    }
}