------------------------------------------------------------
-- Estructuras de Datos
-- Tema 4. Árboles
-- Pablo López
--
-- Plegados de árboles de búsqueda en Haskell
------------------------------------------------------------

module BSTFolds where

import           DataStructures.Graphics.DrawTrees
import           DataStructures.SearchTree.BST

{-
   Disponemos de tres plegados para árboles binarios de búsqueda,
   según el orden en que se visiten los nodos:

     foldPreOrder  :: (a -> b -> b) -> b -> BST a -> b
     foldInOrder   :: (a -> b -> b) -> b -> BST a -> b
     foldPostOrder :: (a -> b -> b) -> b -> BST a -> b

   Observa que el tipo de las tres funciones es idéntico. Su
   funcionamiento es similar al de otros plegados.

   Para el caso base (BST vacío), la solución viene dada por el
   segundo parámetro:

                        BST a plegar    tipo de la solución
                                \         /
          (a -> b -> b) -> b -> BST a -> b
                            \
                   solución para el caso base

   Para el caso recursivo, la función de plegado (a -> b -> b) va
   añadiendo uno a uno nodos a la solución de los nodos ya visitados:

                        BST a plegar    tipo de la solución
                                \         /
          (a -> b -> b) -> b -> BST a -> b
          /      \
    nodo que se   \
   está visitando  \
    actualmente     solución para los nodos ya visitados
                    (los que suceden al que se está visitando actualmente)

  El funcionamiento es como si aplicáramos un `foldr` al recorrido correspondiente.
  Por ejemplo, para el árbol `t0`:

                  1
                /   \
              -3     5

  Tenemos los recorridos:

        preorden:  [1, -3, 5]
        inorden:   [-3, 1, 5]
        postorden: [-3, 5, 1]

  Si plegamos para restar los elementos unos de otros obtenemos:

      foldPreOrder  (-) 0 t0 =  1 - (-3 - (5 - 0)) =  9
      foldInOrder   (-) 0 t0 = -3 - ( 1 - (5 - 0)) =  1
      foldPostOrder (-) 0 t0 = -3 - ( 5 - (1 - 0)) = -7
-}

t0 :: BST Integer
t0 = mkBST [1, -3, 5]

t1 :: BST Integer
t1 = mkBST [6, 5, 7, 3, 4, 10, 8]

-- |
-- >>> sumBST t0
-- 3
-- >>> sumBST t1
-- 43
sumBST :: Num b => BST b -> b
sumBST tree = foldInOrder (+) 0 tree

-- |
-- >>> weightBST t1
-- 7
weightBST :: BST a -> Integer
weightBST tree = foldInOrder (\_ acc -> 1 + acc) 0 tree


-- |
-- >>> maxBST t0
-- Just 5
-- maxBST empty
-- Nothing
maxBST :: Ord a => BST a -> Maybe a
maxBST tree = foldInOrder f Nothing tree
      where
        f x acc = case acc of
          Nothing -> Just x
          Just y  -> Just (max x y)

-- |
-- >>> evenBST t1
-- [6,4,10,8]
evenBST :: Integral a => BST a -> [a]
evenBST tree = foldInOrder f [] tree
    where
      f x xs
        | even x   = x:xs
        |otherwise = xs

-- |
-- >>> filterBST odd t1
-- [5,3,7]
filterBST :: (a->Bool) -> BST a -> [a]
filterBST p tree = foldPreOrder f [] tree
    where
      f x acc
        | p x       = x:acc
        | otherwise = acc   