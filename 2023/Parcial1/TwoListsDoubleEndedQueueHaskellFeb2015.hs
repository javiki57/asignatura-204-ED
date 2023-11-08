-------------------------------------------------------------------------------
-- Estructuras de Datos. Grado en Informática, IS e IC. UMA.
-- Examen de Febrero 2015.
--
-- Implementación del TAD Deque
--
-- Apellidos: 
-- Nombre: 
-- Grado en Ingeniería de Software
-- Grupo:
-- Número de PC: 
-------------------------------------------------------------------------------
module TwoListsDoubleEndedQueue
( DEQue
, empty
, isEmpty
, first
, last
, addFirst
, addLast
, deleteFirst
, deleteLast
) where
import Prelude hiding (last)
import Data.List(intercalate)
import Test.QuickCheck
data DEQue a = DEQ [a] [a]

-- Complexity: O(1)
empty :: DEQue a
empty = undefined

-- Complexity: O(1)
isEmpty :: DEQue a -> Bool
isEmpty =undefined

-- Complexity: O(1)
addFirst :: a -> DEQue a -> DEQue a
addFirst = undefined
-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast = undefined
-- Complexity: O(1)
first :: (Eq a) => DEQue a -> a
first = undefined
-- Complexity: O(1)
last :: (Eq a) => DEQue a -> a
last = undefined

{-
-- Complexity: O(n)
deleteFirst :: (Eq a) => DEQue a -> DEQue a
deleteFirst = undefined
-}

deleteFirst :: DEQue a -> DEQue a
deleteFirst = undefined
-- Complexity: O(n)

{-
deleteLast :: (Eq a) => DEQue a -> DEQue a
deleteLast (DEQ xs ys)
| not (ys == []) = (DEQ xs (tail ys))
| otherwise = aux xs ys 1
where
aux (x:xs) ys n
| n < div (length xs) 2 = aux xs (x:ys) (n+1)
| otherwise
= DEQ ys (reverse xs)
-}
deleteLast :: DEQue a -> DEQue a
deleteLast = undefined


instance (Show a) => Show (DEQue a) where
    show q = "TwoListsDoubleEndedQueue(" ++ intercalate "," [show x | x <- toList q] ++ ")"

toList :: DEQue a -> [a]
toList = undefined


instance (Eq a) => Eq (DEQue a) where
    q == q' = toList q == toList q'
    
instance (Arbitrary a) => Arbitrary (DEQue a) where
    arbitrary = do
        xs <- listOf arbitrary
        ops <- listOf (oneof [return addFirst, return addLast])
        return (foldr id empty (zipWith ($) ops xs))
