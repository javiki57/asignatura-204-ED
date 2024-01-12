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
empty = DEQ [] []

-- Complexity: O(1)
isEmpty :: DEQue a -> Bool
isEmpty (DEQ [] []) = True
isEmpty _     = False

-- Complexity: O(1)
addFirst :: a -> DEQue a -> DEQue a
addFirst x (DEQ xs ys) = DEQ (x:xs) ys 

-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast y (DEQ xs ys) = DEQ xs (y:ys)

-- Complexity: O(1)
first :: (Eq a) => DEQue a -> a
first (DEQ [] []) = error "Lista vacia"
first (DEQ (x:xs) ys) = x
first (DEQ _ (t:[])) = t
first (DEQ _ (t:ts)) = first (DEQ [] ts)


-- Complexity: O(1)
last :: (Eq a) => DEQue a -> a
last (DEQ [] []) = error "Lista vacia"
last (DEQ _ (y:ys)) = y
last (DEQ (x:[]) _ ) = x
last (DEQ (x:xs) _ ) = last (DEQ xs [])

{-
-- Complexity: O(n)
deleteFirst :: (Eq a) => DEQue a -> DEQue a
deleteFirst = undefined
-}

deleteFirst :: DEQue a -> DEQue a
deleteFirst (DEQ [] [])     = empty
deleteFirst (DEQ (x:xs) ys) = DEQ xs ys
deleteFirst (DEQ [] (y:[])) = empty
deleteFirst (DEQ [] ys)     = DEQ [] (reverse (tail (reverse ys)))


-- Complexity: O(n)
deleteLast :: DEQue a -> DEQue a
deleteLast (DEQ [] [] )    = empty
deleteLast (DEQ _ (y:[]))  = empty
deleteLast (DEQ (x:[]) []) = empty
deleteLast (DEQ xs [])     = DEQ (reverse (tail (reverse xs))) []
deleteLast (DEQ xs (y:ys)) = DEQ xs ys


instance (Show a) => Show (DEQue a) where
    show q = "TwoListsDoubleEndedQueue(" ++ intercalate "," [show x | x <- toList q] ++ ")"

toList :: DEQue a -> [a]
toList (DEQ xs ys) = xs ++ (reverse ys)


instance (Eq a) => Eq (DEQue a) where
    q == q' = toList q == toList q'
    
instance (Arbitrary a) => Arbitrary (DEQue a) where
    arbitrary = do
        xs <- listOf arbitrary
        ops <- listOf (oneof [return addFirst, return addLast])
        return (foldr id empty (zipWith ($) ops xs))
