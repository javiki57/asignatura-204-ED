-- =====================================================
-- ============ Puntuación máxima 1.50 =================
-- =====================================================
-- Un Dock es una secuencia de elementos en el que hay uno destacado (sign)
-- Las operaciones disponibles para un dock son:
-- Borrar el elemento destacado, añadir un nuevo elemento delante o detrás del destacado,
-- cambiar el elemento destacado al elemento anterior o al siguiente,
-- saber si la secuencia es vacia o si el destacado es el primero o el último
-- y crear un dock a partir de una lista

{-IMPORTANTE 

Añadir a LinearQueue.hs la siguiente funcion para encolar un elemento al comienzo de la lista

    enqueueFront :: a -> Queue a -> Queue a
    enqueueFront x Empty = Node x Empty
    enqueueFront x q = Node x q
-}

module DataStructures.Dock.TwoQueueDock (
    empty, -- :: Dock a
    isEmpty, -- :: Dock a -> Bool
    sign, -- :: Dock a -> a
    isFirst, -- :: Dock a -> Bool
    isLast, -- :: Dock a -> Bool
    left, -- :: Dock a -> Dock a
    right, -- :: Dock a -> Dock a
    delete, -- :: Dock a -> Dock a
    insertl, -- :: a -> Dock a -> Dock a
    insertr, -- :: a -> Dock a -> Dock a
    listToDock -- :: [a] -> Dock a
) where

import Data.List(intercalate)
import qualified DataStructures.Queue.LinearQueue as Q
import Test.QuickCheck

-- Vamos a implementar el dock con dos pilas:
-- En la primera pila estarán los elementos anteriores al destacado siendo
-- el mas cercano al destacado el que está en la cima.
-- En la segunda pila, el destacado será la cima e seguirán el resto siendo
-- el de la derecha del destacado el que está el siguiente en la pila.
-- INVARIANTES:
-- Siempre hay un elemento destacado (salvo en el dock vacío).
-- Siempre mantendremos el elemento destacado en la cima de la segunda pila.

data Dock a = D (Q.Queue a) (Q.Queue a) deriving Eq
-- Ejemplo. Si tenemos el dock: 1 2 3 <4> 5 donde el elemento destacado es el 4, se presentará por el dato
-- D Q.enqueue 3 $ Q.enqueue 2 $ Q.enqueue 1 Q.empty) (Q.enqueue 4 $ Q.enqueue 5 Q.empty)
-- Su show será TwoStackDock(1,2,3,<4>,5)
-- y las pilas se verán asi:
-- 3
-- 2 4 <- el destacado siempre en la cima de la segunda pila
-- 1 5
-- ----- -----
-- NOTA: Todas las operaciones con pilas están cualificadas con S: Q.Stack, Q.push, Q.pop, etc. --(1,2,3)  (<4>,5)
--                                                              Destacado

sample1 = D (Q.enqueue 1 $ Q.enqueue 2 $ Q.enqueue 3 Q.empty) (Q.enqueue 5 $ Q.enqueue 4 Q.empty) 

sample2 = D (Q.empty) (Q.enqueue 8 $ Q.enqueue 5 $ Q.enqueue 4 Q.empty)
sample3 = D (Q.empty) ( Q.enqueue 4  Q.empty)
sampleVacio = D (Q.empty) (Q.empty)

l1=[1,2,3,4,5]

-- Ejercicio 1 (0.05 ptos.)
-- Crea una dock vacío
empty :: Dock a
empty = D (Q.empty) (Q.empty)

-- ===========================================================
-- Ejercicio 2 (0.05 ptos.)
--Determina si un dock esta vacio
isEmpty :: Dock a ->Bool
isEmpty (D q1 q2) = Q.isEmpty q1 && Q.isEmpty q2

-- ===========================================================
-- Ejercicio 3 (0.10 ptos.)
--Devuelve el elemento destacado. Error si esta vacio
sign :: Dock a -> a 
sign (D _ q2) = Q.first q2

{-
Prelude (Dock.hs)> sign sample1
4
-}

-- ===========================================================
-- Ejercicio 4 (0.10 ptos.)
--Devuelve cierto si el destacado es el primero (o esta vacia)

isFirst :: Dock a -> Bool
isFirst (D q1 q2) = Q.isEmpty q1 || (Q.isEmpty q1 && Q.isEmpty q2)

{-
Prelude (Dock.hs)> isFirst sample1
False
-}

-- ===========================================================
-- Ejercicio 5 (0.10 ptos.)
-- Devuelve cierto si el destacado es el ultimo (o esta vacia)
isLast :: Dock a -> Bool
isLast (D q1 q2)
        | Q.isEmpty (Q.dequeue q2) = True
        | Q.isEmpty q1 && Q.isEmpty q2 = True
        | otherwise = False

{-
Prelude (Dock.hs)> isLast sample1
False
-}
-- ===========================================================
-- Ejercicio 6 (0.10 ptos.)
-- cambia el elemento destacado que pasa a ser el de la izquierda.
-- Si esta vacio o el destacado es el primero lo deja igual

left :: Dock a -> Dock a 
left q@(D q1 q2)
        | isEmpty q = q
        | Q.isEmpty q1 = q
        | otherwise = D (Q.dequeue q1) (Q.enqueueFront (Q.first q1) q2) 

-- (1,2,3<4>,5) -> (3,2,1) (4,5)
-- (2,1) (<3>,4,5) -> (1,2,<3>,4,5)

{-
Prelude (Dock.hs)> left sample1
TwoStackDock(1,2,<3>,4,5)
Prelude (Dock.hs)> left $ left sample1
TwoStackDock(1,<2>,3,4,5)
Prelude (Dock.hs)> left $ left $  left sample1
TwoStackDock(<1>,2,3,4,5)
Prelude (Dock.hs)> isFirst $ left $ left $  left sample1
True
-}

-- ===========================================================
-- Ejercicio 7 (0.25 ptos.)
-- El elemento destacado pasa a ser el de la derecha.
-- Si está vacío o el destacado es el último lo deja igual

right :: Dock a -> Dock a
right q@(D q1 q2)
        | isEmpty q = q
        | Q.isEmpty (Q.dequeue q2) = q
        | otherwise = D (Q.enqueueFront (Q.first q2) q1) (Q.dequeue q2)


{-
Prelude (Dock.hs)> right sample1
TwoStackDock(1,2,3,4,<5>)
Prelude (Dock.hs)> right $ right sample1
TwoStackDock(1,2,3,4,<5>)
Prelude (Dock.hs)> isLast $ right sample1
True
-}
-- ===========================================================
-- Ejercicio 8 (0.25 ptos.)
-- Elimina el objeto destacado. El destacado pasa a ser el siguiente.
-- Si no hay siguiente pasa a ser el anterior.
-- Si queda vacía no hay destacado.
-- Error si está vacía
delete :: Dock a -> Dock a
delete q@(D q1 q2)
        | isEmpty q = error "Esto esta vacio como su corazón"
        | isLast q = if Q.isEmpty q1 then empty else D (Q.dequeue q1) (Q.enqueueFront (Q.first q1) (Q.dequeue q2))
        | otherwise = D q1 (Q.dequeue q2)

-- (3,2,1) (<4>,5) 

{-
Prelude (Dock.hs)> delete sample1
TwoStackDock(1,2,3,<5>)
Prelude (Dock.hs)> delete $ delete sample1
TwoStackDock(1,2,<3>)
Prelude (Dock.hs)> delete $ delete $ delete sample1
TwoStackDock(1,<2>)
Prelude (Dock.hs)> delete $ delete $ delete $ delete sample1
TwoStackDock(<1>)
Prelude (Dock.hs)> delete $ delete $ delete $ delete $ delete sample1
TwoStackDock()
-}
-- ===========================================================
-- Ejercicio 9 (0.15 ptos.)
-- inserta el elemento a la izquierda del destacado y este elemento pasa a ser el destacado
insertl :: a -> Dock a -> Dock a
insertl n (D q1 q2) = D q1 (Q.enqueueFront n q2)

-- ===========================================================
-- Ejercicio 10 (0.15 ptos.)
-- inserta el elemento a la derecha del destacado y este elemento pasa a ser el destacado
insertr :: a -> Dock a -> Dock a
insertr n (D q1 q2) = (D (Q.enqueueFront (Q.first q2) q1) (Q.enqueueFront n (Q.dequeue q2)))

 {-
Prelude (Dock.hs)> insertl 8 sample1
TwoStackDock(1,2,3,<8>,4,5)
Prelude (Dock.hs)> insertr 9 $ insertl 8 sample1
TwoStackDock(1,2,3,8,<9>,4,5)
Prelude (Dock.hs)> insertr 9 $ right sample1
TwoStackDock(1,2,3,4,5,<9>)
Prelude (Dock.hs)> insertl 5 empty
TwoStackDock(<5>)
-}

-- ===========================================================
-- Ejercicio 11 (0.05 ptos.)
-- genera un dock con los elementos de la lista. El destacado será el primero de la lista
listToDock :: [a] -> Dock a
listToDock xs = foldr (insertl) empty xs



{-
Prelude (Dock.hs)> listToDock [1..5]
TwoStackDock(<1>,2,3,4,5)
-}
-- =============================================================================
-- ========================= NO TOCAR DE AQUÍ PARA ABAJO =======================



instance (Show a) => Show (Dock a) where
    show d@(D si sd) | isEmpty d = "TwoStackDock()"
    show (D si sd) = "TwoStackDock("++(intercalate "," (reverse (aux si))) ++(if ei then "<" else ",<") ++ y ++(if ed then ">" else ">,") ++ (intercalate"," ys)++ ")"
        where
            aux s
                    | Q.isEmpty s = []
                    | otherwise = show x : aux s'
                where
                    x = Q.first s
                    s' = Q.dequeue s
            (y:ys) = aux sd
            ei = Q.isEmpty si
            ed = Q.isEmpty (Q.dequeue sd)

instance Arbitrary a => Arbitrary (Dock a) where
    arbitrary = do
        xs <- listOf arbitrary
        return (foldr insertl empty xs)




