-------------------------------------------------------------------------------
-- Ford-Fulkerson Algorithm. Maximal flow for a weighted directed graph.
--
-- Student's name:
-- Student's group:
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.Graph.FordFulkerson where

import Data.List  ((\\))
import DataStructures.Graph.WeightedDiGraph
import DataStructures.Graph.WeightedDiGraphBFT

maxFlowPath :: Path (WDiEdge a Integer) -> Integer
maxFlowPath [] = error "El camino está vacío."
maxFlowPath path = minimum [w | E _ w _ <- path]

updateEdge ::(Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdge x y p [] = [E x p y]
updateEdge x y p (h:hs)
        | (x,y) == getOrigDest h = (updateWeight p h) ++ hs
        | otherwise              = h : (updateEdge x y p hs)

getOrigDest :: WDiEdge a Integer -> (a,a)
getOrigDest (E x p y) = (x,y)

updateWeight :: Integer -> WDiEdge a Integer -> [WDiEdge a Integer]
updateWeight p (E x w y)
    | p + w == 0 = []
    | otherwise = [E x (w + p) y]

updateEdges :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdges [] _ edges = edges
updateEdges (p:ps) w edges = updateEdges ps w (updateEdge x y w edges)
    where
        (x,y) = getOrigDest p

addFlow :: (Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlow = undefined

addFlows :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlows = undefined

fordFulkerson :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [WDiEdge a Integer]
fordFulkerson = undefined

maxFlow :: (Ord a) => [WDiEdge a Integer] -> a -> Integer
maxFlow = undefined

maxFlowMinCut :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [a] -> Integer
maxFlowMinCut = undefined



-- A partir de aquí hasta el final
-- SOLO para alumnos a tiempo parcial 
-- sin evaluación continua

localEquilibrium :: (Ord a) => WeightedDiGraph a Integer -> a -> a -> Bool
localEquilibrium = undefined

sourcesAndSinks :: (Eq a) => WeightedDiGraph a b -> ([a],[a])
sourcesAndSinks = undefined

unifySourceAndSink :: (Eq a) => WeightedDiGraph a Integer -> a -> a -> WeightedDiGraph a Integer
unifySourceAndSink = undefined
