-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module BinaryTree
  ( BinaryTree
  , empty
  , isEmpty
  , insert
  , mkBST
  -- | todo
  , subTreesInRange -- EXERCISE 2
  ) where

data BinaryTree a = Empty
                  | Node a (BinaryTree a) (BinaryTree a)
                  deriving Show


-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 2

subTreesInRange :: Ord a => BinaryTree a -> a -> a -> Integer
subTreesInRange xs min max = fst $ aux xs min max
    where aux Empty min max = (0,True)
          aux (Node x i d) min max
            | x <= max && x >= min && bi && bd = (1+ni+nd, True)
            | otherwise = (ni+nd, False) 
            where
                (ni,bi) = aux i min max
                (nd,bd) = aux d min max



-- | NO MODIFICAR A PARTIR DE AQUÍ --------------------------------------------
-- | DO NOT MODIFY CODE BELOW      --------------------------------------------

empty :: BinaryTree a
empty  = Empty

isEmpty :: BinaryTree a -> Bool
isEmpty Empty = True
isEmpty _     = False

insert :: Ord a => a -> BinaryTree a -> BinaryTree a
insert x' Empty  =  Node x' Empty Empty
insert x' (Node x lt rt)
    | x'<x       = Node x (insert x' lt) rt
    | x'>x       = Node x lt (insert x' rt)
    | otherwise  = Node x' lt rt

mkBST :: Ord a => [a] -> BinaryTree a
mkBST xs  = foldl (flip insert) empty xs
