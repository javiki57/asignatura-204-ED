-- =====================================================
-- ============ Puntuación máxima 1.25 =================
-- =====================================================
-- Un Dock es una secuencia de elementos en el que hay uno destacado (sign)
-- Las operaciones disponibles para un dock son:
-- Borrar el elemento destacado, añadir un nuevo elemento delante o detrás del destacado,
-- cambiar el elemento destacado al elemento anterior o al siguiente,
-- saber si la secuencia es vacia o si el destacado es el primero o el último
-- y crear un dock a partir de una lista

module DataStructures.Dock.TwoStacksDock  (
    empty,           -- :: Dock a
    isEmpty,         -- :: Dock a -> Bool
    sign,            -- :: Dock a ->  a
    isFirst,         -- :: Dock a -> Bool
    isLast,          -- :: Dock a -> Bool
    left,            -- :: Dock a ->  Dock a
    right,           -- :: Dock a -> Dock a
    delete,          -- :: Dock a ->  Dock a
    insertl,         -- :: a  ->  Dock a ->  Dock a
    insertr,         -- :: a  ->  Dock a ->  Dock a
    listToDock       -- :: [a] -> Dock a
) where

import Data.List(intercalate)
import qualified DataStructures.Stack.LinearStack as S
import Test.QuickCheck
 
-- Vamos a implementar el dock con dos pilas:
-- En la primera pila estarán los elementos anteriores al destacado siendo 
-- el mas cercano al destacado el que está en la cima.
-- En la segunda pila, el destacado será la cima e seguirán el resto siendo 
-- el de la derecha del destacado el que está el siguiente en la pila.
-- INVARIANTES:
-- Siempre hay un elemento destacado (salvo en el dock vacío).
-- Siempre mantendremos el elemento destacado en la cima de la segunda pila.

data Dock a =  D (S.Stack a) (S.Stack a) deriving Eq
-- Ejemplo. Si tenemos el dock: 1 2 3 <4> 5 donde el elemento destacado es el 4, se presentará por el dato
-- D S.push 3 $ S.push 2 $ S.push 1 S.empty) (S.push 4 $ S.push 5 S.empty)
-- Su show será TwoStackDock(1,2,3,<4>,5)
-- y las pilas se verán asi:
--       3           
--       2           4 <- el destacado siempre en la cima de la segunda pila
--       1           5
--     -----       -----
-- NOTA: Todas las operaciones con pilas están cualificadas con S: S.Stack, S.push, S.pop, etc.
sample1 = D (S.push 3 $ S.push 2 $ S.push 1 S.empty) (S.push 4 $ S.push 5 S.empty)

-- ===========================================================
-- Ejercicio 1 (0.05 ptos.)
-- Crea una dock vacío
empty :: Dock a 
empty = D (S.empty) (S.empty)

-- ===========================================================
-- Ejercicio 2 (0.05 ptos.)
-- Determina si un dock está vacío
isEmpty :: Dock a -> Bool
isEmpty (D si sd) = S.isEmpty si && S.isEmpty sd
-- ===========================================================
-- Ejercicio 3 (0.10 ptos.)
-- Devuelve el elemento destacado. Error si está vacío
sign  :: Dock a ->  a
sign d@(D si sd)
        | isEmpty d = error "Vacio"
        | otherwise = S.top sd
    
{-
Prelude (Dock.hs)> sign sample1
4
-}

-- ===========================================================
-- Ejercicio 4 (0.10 ptos.)
-- Devuelve cierto si el destacado es el primero (o está vacía)
isFirst :: Dock a -> Bool
isFirst (D si sd) = S.isEmpty (S.pop si)

{-
Prelude (Dock.hs)> isFirst sample1
False
-}
-- ===========================================================
-- Ejercicio 5 (0.10 ptos.)
-- Devuelve cierto si el destacado es el último (o está vacía)
isLast :: Dock a -> Bool
isLast (D si sd) = S.isEmpty (S.pop sd)

{-
Prelude (Dock.hs)> isLast sample1
False
-}

-- ===========================================================
-- Ejercicio 6 (0.20 ptos.)
-- Cambia el elemento destacado que pasa a ser el de la izquierda.
-- Si está vacío o el destacado es el primero lo deja igual
left :: Dock a ->  Dock a
left d@(D si sd)
            | isFirst d = d
            | otherwise = D (S.pop si) (S.push (S.top si) sd)

--Hacemos un pop de la primera pila y hacemos un push 
--del que quitamos de la primera fila(top) en la segunda fila

{-
Prelude (Dock.hs)> left sample1
TwoStackDock(1,2,<3>,4,5)
Prelude (Dock.hs)> left $ left sample1
TwoStackDock(1,<2>,3,4,5)
Prelude (Dock.hs)> left $ left $ left sample1
TwoStackDock(<1>,2,3,4,5)
Prelude (Dock.hs)> left $ left $ left $ left sample1
TwoStackDock(<1>,2,3,4,5)
Prelude (Dock.hs)> isFirst $ left $ left $ left sample1
True
-}
-- ===========================================================
-- Ejercicio 7 (0.20 ptos.)
-- El elemento destacado pasa a ser el de la derecha.
-- Si está vacío o el destacado es el último  lo deja igual
right :: Dock a ->  Dock a
right d@(D si sd)
        | isLast d = d
        | otherwise = D (S.push (S.top sd) si) (S.pop sd)        

{-
Prelude (Dock.hs)> right sample1
TwoStackDock(1,2,3,4,<5>)
Prelude (Dock.hs)> right $ right sample1
TwoStackDock(1,2,3,4,<5>)
Prelude (Dock.hs)> isLast $ right sample1
True
-}
-- ===========================================================
-- Ejercicio 8 (0.20 ptos.)
-- Elimina el objeto destacado. El destacado pasa a ser el siguiente.
-- Si no hay siguiente pasa a ser el anterior.
-- Si queda vacía no hay destacado.
-- Error si está vacía
delete :: Dock a ->  Dock a
delete d@(D si sd)
        | isEmpty d = error "Vacio"
        | isLast d = if S.isEmpty si then empty else D (S.pop si) (S.push (S.top si) (S.pop sd)) 
        | otherwise = D si (S.pop sd)

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
-- Ejercicio 9 (0.10 ptos.)
-- inserta el elemento a la izquierda del destacado y este elemento pasa a ser el destacado
insertl :: a  ->  Dock a ->  Dock a
insertl x (D si sd) = D (si) (S.push x sd)
-- ===========================================================
-- Ejercicio 10 (0.10 ptos.)
-- inserta el elemento a la derecha del destacado y este elemento pasa a ser el destacado
insertr :: a  ->  Dock a ->  Dock a
insertr x d@(D si sd)
            | isEmpty d = D (si) (S.push x sd)
            | otherwise = D (S.push (S.top sd) si) (S.push x (S.pop sd))

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
listToDock  []    = empty
listToDock (x:xs) = insertl x (listToDock xs)
            
{-
Prelude (Dock.hs)> listToDock [1..5]
TwoStackDock(<1>,2,3,4,5)
-}
-- =============================================================================
-- ========================= NO TOCAR DE AQUÍ PARA ABAJO =======================
instance (Show a) => Show (Dock a) where
    show d@(D si sd) | isEmpty d = "TwoStackDock()"
    show (D si sd) = "TwoStackDock("++(intercalate "," (reverse (aux si))) ++ 
                        (if ei then "<" else ",<") ++ y ++(if ed then ">" else ">,") ++ (intercalate "," ys)++ ")"
        where 
            aux s
                    | S.isEmpty s = []
                    | otherwise = show x : aux s'
                where
                    x = S.top s
                    s' = S.pop s
            (y:ys) = aux sd
            ei = S.isEmpty si
            ed = S.isEmpty (S.pop sd) 

instance Arbitrary a => Arbitrary (Dock a) where
    arbitrary = do  
            xs <- listOf arbitrary
            return (foldr insertl empty xs) 