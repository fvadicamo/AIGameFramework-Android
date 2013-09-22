package com.squirrelapps.aigameframework;

import java.util.Iterator;

/**
 * Created by Francesco Vadicamo on 9/21/13.
 */
public class AlfaBetaPruning extends SearchAlgorithm
{
    private int max = -1;
    //private int min = -1;

    public AlfaBetaPruning(GameAnalyzer gameAnalyzer)
    {
        super(gameAnalyzer);
    }

    protected void search(byte[] currStatus) throws IllegalSearchStateException
    {
        float alfa = Float.NEGATIVE_INFINITY;
        float beta = Float.POSITIVE_INFINITY;

        //min = gameAnalyzer.currentPlayer(currStatus);
        max = gameAnalyzer.nextPlayer(currStatus);

//System.out.println();

        Iterator<byte[]> iter = operators(currStatus).iterator();

        float newalfa;

        byte[] op;

        this.solution = null;

        while(iter.hasNext()){
            checkState();

            op = iter.next();

            if(solution == null)
                this.solution = op;

            newalfa = evaluateMin(apply(op, currStatus), maxDepth - 1, alfa, beta);

//Cell2D c2D = (Cell2D)op.cell;
//StringBuffer sb = new StringBuffer(90);
//sb.append("AlfaBetaPruning >> '").append( (char)('A'+c2D.x) ).append( (c2D.y+1) ).append("' > ");
//if(newalfa <= alfa)
//sb.append("(").append(newalfa).append(" <= ").append(alfa).append(") --> pruned!");
//else
            if(newalfa > alfa){
//sb.append(newalfa);
                alfa = newalfa;
                this.solution = op;
            }

//System.out.println(sb.toString());
        }
    }

//    protected float evaluateMax(GameStatus status, int depth, float alfa, float beta) throws IllegalSearchStateException
//    {
//        checkState();
//
//        if(depth == 0 || isFinalStatus(status))
//            return evaluator.evaluate(status, max);
//
//        Iterator iter = operators(status).iterator();
//        Move op;
//
//        while(iter.hasNext())
//        {
//            op = (Move)iter.next();
//
//            alfa = Math.max( alfa, evaluateMin(apply(op, status), depth-1, alfa, beta) );
//            if(alfa >= beta)
//            {
////System.out.println("AlfaBetaPruning >> pruning on depth: "+currentDepth);
//                return beta;
//            }
//        }
//        return alfa;
//    }

    protected float evaluateMax(byte[] status, int depth, float alfa, float beta) throws IllegalSearchStateException
    {
        checkState();

        if(depth == 0 || isFinalStatus(status))
            return evaluate(status, max);

        Iterator<byte[]> iter = operators(status).iterator();
        byte[] op;

        while(iter.hasNext()){
            op = iter.next();

            alfa = Math.max(alfa, evaluateMin(apply(op, status), depth - 1, alfa, beta));
            if(alfa >= beta){
//System.out.println("AlfaBetaPruning >> pruning on depth: "+currentDepth);
                return beta;
            }
        }
        return alfa;
    }

//    protected float evaluateMin(GameStatus status, int depth, float alfa, float beta) throws IllegalSearchStateException
//    {
//        checkState();
//
//        if(depth == 0 || isFinalStatus(status))
//            return evaluator.evaluate(status, max);
//
//        Iterator iter = operators(status).iterator();
//        Move op;
//
//        while(iter.hasNext())
//        {
//            op = (Move)iter.next();
//
//            beta = Math.min(beta, evaluateMax(apply(op, status), depth-1, alfa, beta));
//
//            if(beta <= alfa)
//            {
////System.out.println("AlfaBetaPruning >> pruning on depth: "+currentDepth);
//                return alfa;
//            }
//        }
//        return beta;
//    }

    protected float evaluateMin(byte[] status, int depth, float alfa, float beta) throws IllegalSearchStateException
    {
        checkState();

        if(depth == 0 || isFinalStatus(status))
            return evaluate(status, max);

        Iterator<byte[]> iter = operators(status).iterator();
        byte[] op;

        while(iter.hasNext()){
            op = iter.next();

            beta = Math.min(beta, evaluateMax(apply(op, status), depth - 1, alfa, beta));

            if(beta <= alfa){
//System.out.println("AlfaBetaPruning >> pruning on depth: "+currentDepth);
                return alfa;
            }
        }
        return beta;
    }

//    protected boolean isMaxTurn(GameStatus status)
//    {
//        return status.getNextPlayer() == max;
//    }
}
