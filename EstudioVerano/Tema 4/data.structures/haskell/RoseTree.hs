------------------------------------------------------------
-- Estructuras de Datos
-- Tema 4. Árboles
-- Pablo López
--
-- Árboles generales (rose trees) en Haskell
------------------------------------------------------------

module RoseTree where

data Tree a = Empty
            | Node a [Tree a]
            deriving Show

gtree1 :: Tree Int
gtree1 =
  Node 1 [ Node 2 [ Node 4 [ ]
                  , Node 5 [ ]
                  , Node 6 [ ]
                  ]
         , Node 3 [ Node 7 [ ] ]
         ]

-- | suma los nodos de un árbol genérico
-- >>> sumT gtree1
-- 28
sumT :: Num a => Tree a -> a
sumT Empty = 0
sumT (Node a s) = a + sum (map sumT s)

-- | altura (número de niveles) de un árbol genérico
-- >>> heightT gtree1
-- 3
heightT :: Tree a -> Int
heightT Empty      = 0
heightT (Node a s) = case s of
        [] -> 1
        xs -> 1 + maximum (map heightT xs)

-- | frontera (conjunto de nodos hoja) de un árbol genérico
-- >>> borderT gtree1
-- [4,5,6,7]
borderT :: Tree a -> [a]
borderT Empty       = []
borderT (Node a []) = [a]
borderT (Node a s)  = concatMap borderT s

-- | recorrido de un árbol genérico
-- >>> flattenT gtree1
-- [1,2,4,5,6,3,7]
flattenT :: Tree a -> [a]
flattenT Empty = []
flattenT (Node a s) = case s of
        [] -> [a]
        xs -> a : (concatMap flattenT xs) 
