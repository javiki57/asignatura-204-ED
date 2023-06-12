-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DiGraphDftTimer(
    diGraphDftTimer
) where

import           DataStructures.Dictionary.AVLDictionary as D
import           DataStructures.Graph.DiGraph
import           DataStructures.Set.BSTSet               as S

-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 3

diGraphDftTimer :: (Ord v) => DiGraph v -> (Dictionary v Int, Dictionary v Int)
diGraphDftTimer diGraph = (arrivalD, departureD)
    where
        (arrivalD, departureD, timer) = diGraphSuccessors (vertices diGraph) D.empty D.empty 0 
        diGraphSuccessors vertices arrivalD departureD timer  =
            foldl (\(arrD,depD,tm) v -> diGraphVertex v arrD depD tm) (arrivalD, departureD, timer) vertices
        diGraphVertex v arrD depD timer 
            | D.isDefinedAt v arrD = (arrD,depD,timer) 
            | otherwise = (arrD'', depD'', timer'+1)
            where
                arrD' = D.insert v timer arrD
                (arrD'',depD',timer') = diGraphSuccessors (successors diGraph v) arrD' depD (timer+1) 
                depD'' = D.insert v timer' depD'