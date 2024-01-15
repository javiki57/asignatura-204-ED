-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.Graph.EulerianCycle(isEulerian, eulerianCycle) where

import DataStructures.Graph.Graph
import Data.List

--H.1)
isEulerian :: Eq a => Graph a -> Bool
isEulerian g = aux (vertices g)
    where
        aux [] = True
        aux (x:xs) = even (degree g x) && aux xs

-- H.2)
remove :: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v, u) = aux (deleteEdge g (v, u)) (vertices g)
  where
    aux grafo [] = grafo
    aux grafo (x:xs)
      | degree grafo x == 0 = aux (deleteVertex grafo x) xs
      | otherwise           = aux grafo xs

-- H.3)
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = aux g v0 [v0]
    where
        aux g v xs
            | u == v0 = (g', u:xs)
            | otherwise = aux g' u (u:xs)
                where
                    g' = remove g (v,u)
                    u = head (successors g v)

-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] ys     = ys
connectCycles (x:xs) (y:ys)
        | x == y    = (y:ys) ++ xs
        | otherwise = x : connectCycles xs (y:ys)

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g xs = head $ filter (\v -> v `elem` xs) (vertices g)

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g
    | not (isEulerian g) = error "Error: Grafo No Euleriano"
    | otherwise = aux g (head (vertices g)) []
        where
            aux g v xs
                | isEmpty g = xs
                | otherwise = aux g' (last xs') xs'
                    where
                        (g', cicloParcial) = extractCycle g v
                        xs' = connectCycles xs cicloParcial
