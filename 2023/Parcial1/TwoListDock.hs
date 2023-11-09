module TwoListDock (
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
import Test.QuickCheck

-- Vamos a implementar el dock con dos pilas:
-- En la primera pila estarán los elementos anteriores al destacado siendo
-- el mas cercano al destacado el que está en la cima.
-- En la segunda pila, el destacado será la cima e seguirán el resto siendo
-- el de la derecha del destacado el que está el siguiente en la pila.
-- INVARIANTES:
-- Siempre hay un elemento destacado (salvo en el dock vacío).
-- Siempre mantendremos el elemento destacado en la cima de la segunda pila.

data Dock a = D ([a]) ([a]) deriving Eq
-- Ejemplo. Si tenemos el dock: 1 2 3 <4> 5 donde el elemento destacado es el 4, se presentará por el dato
-- D (S.enqueue 3 $ S.enqueue 2 $ S.enqueue 1 S.empty) (S.enqueue 4 $ S.enqueue 5 S.empty)
-- Su show será TwoStackDock(1,2,3,<4>,5)
-- y las pilas se verán asi:
-- 3
-- 2 4 <- el destacado siempre en la cima de la segunda pila
-- 1 5
-- ----- -----
-- NOTA: Todas las operaciones con pilas están cualificadas con S: S.Stack, S.push, S.pop, etc. --(1,2,3)  (<4>,5)
--                                                              Destacado

sample1 = D [3,2,1] [4,5]
sample2 = D [] [4]
sampleVacio = D [] []
l1 = [1,2,3,4,5]

-- Ejercicio 1 (0.05 ptos.)
-- Crea una dock vacío
empty :: Dock a
empty = D [] []

-- ===========================================================
-- Ejercicio 2 (0.05 ptos.)
--Determina si un dock esta vacio
isEmpty :: Dock a ->Bool
isEmpty (D [] []) = True
isEmpty _         = False

-- ===========================================================
-- Ejercicio 3 (0.10 ptos.)
--Devuelve el elemento destacado. Error si esta vacio
sign :: Dock a -> a 
sign d@(D xs (y:ys))
        | isEmpty d = error "Listas vacías"
        | otherwise = y

{-
Prelude (Dock.hs)> sign sample1
4
-}

-- ===========================================================
-- Ejercicio 4 (0.10 ptos.)
--Devuelve cierto si el destacado es el primero (o esta vacia)

isFirst :: Dock a -> Bool
isFirst (D [] []) = True
isFirst (D [] ys) = True 
isFirst (D xs _ ) = False


{-
Prelude (Dock.hs)> isFirst sample1
False
-}

-- ===========================================================
-- Ejercicio 5 (0.10 ptos.)
-- Devuelve cierto si el destacado es el ultimo (o esta vacia)
isLast :: Dock a -> Bool
isLast (D [] [])    = True
isLast (D _ (y:[])) = True
isLast (D _ ys)     = False


{-
Prelude (Dock.hs)> isLast sample1
False
-}
-- ===========================================================
-- Ejercicio 6 (0.10 ptos.)
-- cambia el elemento destacado que pasa a ser el de la izquierda.
-- Si esta vacio o el destacado es el primero lo deja igual

left :: Dock a -> Dock a 
left (D [] ys )        = D [] ys
left (D (x:xs) (y:ys)) = D xs (x:y:ys)


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
right (D xs [] )        = D xs []
right (D xs (y:[]))     = D xs (y:[])
right (D (x:xs) (y:ys)) = D (y:x:xs) ys



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
delete (D [] [] )        = error "El dock está vacío"
delete (D (x:xs) (y:[])) = D xs [x]
delete (D xs (y:ys))     = D xs ys


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
insertl n (D xs ys ) = D xs (n:ys)

-- ===========================================================
-- Ejercicio 10 (0.15 ptos.)
-- inserta el elemento a la derecha del destacado y este elemento pasa a ser el destacado
insertr :: a -> Dock a -> Dock a
insertr n (D xs (y:ys)) = D (y:xs) (n:ys)
insertr n (D xs [])     = D xs [n]

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
    show d@(D si sd) | isEmpty d = "TwoListDock()"
    show (D si sd) = "TwoListDock("++(intercalate "," (map show (reverse si))) ++ 
                        (if ei then "<" else ",<") ++ show y ++(if ed then ">" else ">,") ++ (intercalate "," (map show ys))++ ")"
        where 
            (y:ys) = sd
            ei = null si
            ed = null (tail sd) 


instance Arbitrary a => Arbitrary (Dock a) where
    arbitrary = do  
            xs <- listOf arbitrary
            return (D [] xs)
