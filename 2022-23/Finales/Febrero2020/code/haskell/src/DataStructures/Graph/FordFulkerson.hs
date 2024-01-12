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
maxFlowPath path = minimum [ w | (E _ w _ ) <- path ]

updateEdge ::(Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdge x y p [] = [E x p y]
updateEdge x y p ((E f w t):xs)
                | pertenece && not sumacero = (E f (w+p) t):xs
                | pertenece && sumacero     = xs
                | otherwise                 = (E f w t) : (updateEdge x y p xs)
          where
            pertenece = x==f && y==t
            sumacero = (w+p) == 0


updateEdges :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdges path p edges = foldr (\(E f w t) acc -> updateEdge f t p acc) edges path

addFlow :: (Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlow x y p [] = [E x p y]
addFlow x y p ((E f w t):sol)
          | pertenece = (E f (w+p) t):sol
          | contrario = sol
          | contrario && menor = (E f (p-w) t):sol
          | contrario && mayor = (E f (w-p) t):sol
          | otherwise = addFlow x y p sol
        where
          pertenece = x==f && y==t
          contrario = x==t && y==f && w==p
          menor = w < p
          mayor = w > p


addFlows :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlows path p sol = foldr (\(E f w t) -> addFlow f t p ) sol path

fordFulkerson :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [WDiEdge a Integer]
fordFulkerson g src dst = undefined

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

