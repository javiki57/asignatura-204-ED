-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module WBinTree( WBinTree
               , empty
               , insert
               , isWeightBalanced
               , mkWBinTree
               ) where

data WBinTree a = Empty
                | Node Int a (WBinTree a) (WBinTree a)
                deriving Show

-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 4

isWeightBalanced :: WBinTree a -> Bool
isWeightBalanced Empty = True
isWeightBalanced (Node n v i d) = 
    isWeightBalanced i && isWeightBalanced d &&
    nodos i >= nodos d && abs(nodos i - nodos d) <= 1

nodos Empty = 0
nodos (Node n x i d) = n

insert :: a -> WBinTree a -> WBinTree a
insert x Empty = Node 1 x Empty Empty
insert x (Node n v i d) 
    | nodos i > nodos d = Node (n+1) v i (insert x d)
    | otherwise = Node (n+1) v (insert x i) d



-- | NO MODIFICAR A PARTIR DE AQUÍ --------------------------------------------
-- | DO NOT MODIFY CODE BELOW      --------------------------------------------

empty :: WBinTree a
empty = Empty

mkWBinTree :: [a] -> WBinTree a
mkWBinTree = foldr insert empty
