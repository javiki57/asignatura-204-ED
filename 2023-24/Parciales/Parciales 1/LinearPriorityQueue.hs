-------------------------------------------------------------------------------
-- Linear implementation of Priority Queues
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.PriorityQueue.LinearPriorityQueue
  ( PQueue
-- basicas  
  , empty    -- 0.25
  , isEmpty  -- 0.25
  , first    -- 0.25
  , dequeue  -- 0.25
  , enqueue  -- 0,50
-- extras
  , mkPQ     -- 1.00 (1.00 con plegado. 0.75 en otro caso)
  , mapPQ    -- 1.00 (1.00 con plegado. 0.75 en otro caso)
  , filterPQ -- 0,75
  , foldrPQ  -- 0,75
  , fromPQ   -- 1.00 (1.00 recursiva. 0.25 con filterPQ)
  , toPQ     -- 1.00 (1.00 recursiva. 0.25 con filterPQ)
  , toList   -- 1.00 (1.00 con plegado. 0.75 en otro caso)
  ) where

import Data.List(intercalate)
import Test.QuickCheck

data PQueue a = Empty | Node a (PQueue a)

-- empty. Crea una cola de prioridad vacia.
-- 0.25 
empty ::PQueue a
empty = Empty

-- isEmpty. Comprueba si una cola de prioridad esta vacia.
-- 0.25
isEmpty ::PQueue a -> Bool
isEmpty Empty = True
isEmpty _  = False

-- enqueue. Encola un elemento en una cola de prioridad.
-- 0.50
enqueue :: (Ord a) => a ->PQueue a -> PQueue a
enqueue n Empty = Node n Empty
enqueue n (Node x q)
        | n <= x    = Node n (Node x q)
        | otherwise = Node x (enqueue n q)



-- first. Devuelve el primer elemento de una cola de prioridad.
-- 0.25
first :: PQueue a -> a
first Empty      = error "Cola vacía"
first (Node x q) = x


-- dequeue. Desencola el primer elemento de una cola de prioridad.
-- 0.25
dequeue :: PQueue a -> PQueue a
dequeue Empty = Empty
dequeue (Node x q) = q


-- mkPQ. Crea una cola de prioridad con los elementos de la lista.
-- 1.00 (1.00 con plegado. 0.75 en otro caso)
mkPQ :: (Ord a) => [a] -> PQueue a
mkPQ xs = foldr (enqueue) Empty xs


-- mapPQ. Transforma una cola de prioridad en otra aplicando la
-- funcion dada a cada elemento.
-- Cuidado con esta función. Hay que mantener que sea cola de prioridad
-- 1.00 (1.00 con plegado, 0.75 en otro caso)
mapPQ :: (Ord a, Ord b) => (a -> b) -> PQueue a -> PQueue b
mapPQ f Empty      = Empty
mapPQ f (Node x q) = enqueue (f x) (mapPQ f q)



-- filterPQ. Crea una cola de prioridad con los elementos de la cola 
-- de prioridad dada que cumplen el predicado.
-- 0.75
filterPQ :: Ord a => (a -> Bool) -> PQueue a -> PQueue a
filterPQ f Empty      = Empty
filterPQ f (Node x q)
        | f x       = enqueue x (filterPQ f q)
        | otherwise = filterPQ f q


-- foldrPQ. Realiza un plegado por la derecha de la cola de prioridad
-- usando la funcion y el caso base dados
-- 0.75 
foldrPQ :: (Ord a) => (a -> b -> b) -> b -> PQueue a -> b
foldrPQ f z Empty      = z
foldrPQ f z (Node x q) = f x (foldrPQ f z q)

-- fromPQ. Devuelve una cola a partir de los elementos que son mayores 
-- o iguales a uno dado
-- No es buena solución hacerlo usando filterPQ
-- 1.00 (1.00 recursiva. 0.25 con filterPQ)
fromPQ :: Ord a => a -> PQueue a -> PQueue a
fromPQ n Empty = Empty
fromPQ n (Node x q)
        | x >= n    = Node x (fromPQ n q)
        | otherwise = fromPQ n q

-- toPQ. Devuelve una cola hasta los elementos menores que uno dado.
-- No es buena solución hacerlo usando filterPQ
-- 1.00 (1.00 recursiva. 0.25 con filterPQ)
toPQ :: Ord a => a -> PQueue a -> PQueue a
toPQ n Empty = Empty
toPQ n (Node x q)
        | x < n    = Node x (toPQ n q)
        | otherwise = toPQ n q



listToPQ ::Ord a =>[a]->PQueue a 
listToPQ xs = foldr (enqueue) Empty xs

-- toList. Devuelve una lista con los elementos de la cola
-- Es buena solución hacerlo con un plegado
-- 1.00 (1.00 con plegado. 0.75 en otro caso)
toList :: Ord a => PQueue a -> [a]
toList xs = foldrPQ (:) [] xs

--toList = foldrPQ (\e )

-- p_pqinversa. Propiedades que comprueban si mkPQ y toList son inversas una 
-- de la otra
-- 0.50
p_pqinversa1 :: (Ord a) => PQueue a -> Bool
p_pqinversa1 q = undefined
p_pqinversa2 :: (Ord a) => [a] -> Bool
p_pqinversa2 xs = undefined

-- Tabla de complejidades
-- Completar 
-- 0.50
-- | Operación | Complejidad |
-- |-----------|:-----------:|
-- | empty     |    O(1)     |
-- | isEmpty   |    O(1)     |
-- | enqueue   |    O(n)     |
-- | first     |    O(1)     |
-- | dequeue   |    O(1)     |
-- | mkPQ      |    O(n)     |
-- | mapPQ     |    O(n2)    |
-- | filterPQ  |    O(n)     |
-- | foldrPQ   |    O(n)     |
-- | fromPQ    |    O(n)     |
-- | toPQ      |    O(n)     |
-- | toList    |    O(n)     |

-- quitar estos comentarios para probar

-- ===============
-- Ejemplos de uso
-- ===============
s1 = mkPQ [2,5,4,7,9,8,1,6,8,5,7]
--LinearPQueue(1,2,4,5,5,6,7,7,8,8,9)

s2 = enqueue 3 (enqueue 0 s1)
-- LinearPQueue(0,1,2,3,4,5,5,6,7,7,8,8,9)




s3 = dequeue s2
-- LinearPQueue(1,2,3,4,5,5,6,7,7,8,8,9)

s4 = mapPQ (\x -> x `mod` 5) s3
-- LinearPQueue(0,0,1,1,2,2,2,3,3,3,4,4)

s5 = filterPQ even s4
-- LinearPQueue(0,0,0,2,2,2,4,4)

s6 = foldrPQ (-) 0 s5
-- -2

s7 = foldrPQ (\e s -> even e && s) True s5
-- True

s8 = fromPQ 5 s1
-- LinearPQueue(5,5,6,7,7,8,8,9)

s9 = toPQ 5 s1
-- LinearPQueue(1,2,4)

s10 = toList s1
-- [1,2,4,5,5,6,7,7,8,8,9]











-- ===============================================
--   NO TOCAR ESTA PARTE
-- ===============================================

-- Showing a priority PQueue
instance (Show a) => Show (PQueue a) where
  show q = "LinearPQueue(" ++ intercalate "," (aux q) ++ ")"
    where
     aux Empty      =  []
     aux (Node x q) =  show x : aux q

-- Priority Queue equality
instance (Eq a) => Eq (PQueue a) where
  Empty      == Empty           =  True
  (Node x q) == (Node x' q')    =  x==x' && q==q'
  _          == _               =  False

-- This instance is used by QuickCheck to generate random Priority Queues
instance (Ord a, Arbitrary a) => Arbitrary (PQueue a) where
    arbitrary =  do
      xs <- listOf arbitrary
      return (foldr enqueue empty xs)
