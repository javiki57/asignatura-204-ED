-- =====================================================
-- ============ Puntuación máxima 1.5 =================
-- =====================================================
-- Un Dock es una secuencia de elementos en el que hay uno destacado (sign)
-- Las operaciones disponibles para un dock son:
-- Borrar el elemento destacado, añadir un nuevo elemento delante o detrás del destacado,
-- cambiar el elemento destacado al elemento anterior o al siguiente,
-- saber si la secuencia es vacia o si el destacado es el primero o el último
-- y crear un dock a partir de una lista

module DataStructures.Dock.TwoStackDock  (
    empty,           -- :: Dock a
    isEmpty,         -- :: Dock a -> Bool
    sign,            -- :: Dock a ->  a
    isFirst,         -- :: Dock a -> Bool
    isLast,          -- :: Dock a -> Bool
    left,            -- :: Dock a ->  Dock a
    right,           -- :: Dock a -> Dock a
    delete,          -- :: Dock a ->  Dock a
    insertl,         -- :: a  ->  Dock a ->  Dock a
    dockToList,      -- :: Dock a -> [a]
    listToDock       -- :: [a] -> Dock a
    
) where

import           Data.List                        (intercalate)
import           Test.QuickCheck

-- INVARIANTES:
-- Siempre hay un elemento destacado (salvo en el dock vacío)
-- Siempre mantendremos el elemento destacado en la primera posición de la primera lista.

data Dock a =  D [a] [a]
-- Ejemplo. Si tenemos el dock: 1 2 3 <4> 5 donde el elemento destacado es el 4, se presentará por el dato
-- D [4,3,2,1] [5]
-- Su show será TwoListDock(1,2,3,<4>,5)

sample1 = D [4,3,2,1] [5]

sample2 = D [3] []

sample3 = D [1] [5,6,7]

sample4 = D [5,1,2,3] [7]

sampleEmpty = D [] []

-- ===========================================================
-- Ejercicio 1 (0.05 ptos.)
-- Crea una dock vacío 

empty :: Dock a
empty = D [] []

-- ===========================================================
-- Ejercicio 2 (0.05 ptos.)
-- Determina si un dock está vacío 

isEmpty :: Dock a -> Bool
isEmpty (D [] []) = True
isEmpty _         = False

-- ===========================================================
-- Ejercicio 3 (0.10 ptos.)
-- Devuelve el elemento destacado. Error si está vacío

sign  :: Dock a ->  a
sign (D [] _ ) = error "No hay elemento destacado"
sign (D (x:xs) _ ) =  x

{-
Prelude (Dock.hs)> sign sample1
4
-}

-- ===========================================================
-- Ejercicio 4 (0.10 ptos.)
-- Devuelve cierto si el destacado es el primero (o está vacía)

isFirst :: Dock a -> Bool
isFirst (D (x:[]) _) = True
isFirst (D (x:xs) _) = False
isFirst (D [] ys) = False

{-
Prelude (Dock.hs)> isFirst sample1
False
-}
-- ===========================================================
-- Ejercicio 5 (0.10 ptos.)
-- Devuelve cierto si el destacado es el último (o está vacía)

isLast :: Dock a -> Bool
isLast (D (x:[]) []) = True
isLast (D xs [] )    = True
isLast (D xs ys)     = False

{-
Prelude (Dock.hs)> isLast sample1
False
-}

-- ===========================================================
-- Ejercicio 6 (0.20 ptos.)
-- Cambia el elemento destacado que pasa a ser el de la izquierda.
-- Si está vacío o el destacado es el primero lo deja igual

left :: Dock a ->  Dock a
left (D (x:[]) ys) = (D (x:[]) ys)
left (D (x:xs) ys )  = D xs (x:ys)


{-
Prelude (Dock.hs)> left sample1
TwoListDock(1,2,<3>,4,5)
Prelude (Dock.hs)> left $ left sample1
TwoListDock(1,<2>,3,4,5)
Prelude (Dock.hs)> left $ left $ left sample1
TwoListDock(<1>,2,3,4,5)
Prelude (Dock.hs)> left $ left $ left $ left sample1
TwoListDock(<1>,2,3,4,5)
Prelude (Dock.hs)> isFirst $ left $ left $ left sample1
True
-}
-- ===========================================================
-- Ejercicio 7 (0.20 ptos.)
-- El elemento destacado pasa a ser el de la derecha.
-- Si está vacío o el destacado es el último  lo deja igual

right :: Dock a ->  Dock a
right d@(D xs []) = d
right (D xs (y:ys)) = D (y:xs) ys

{-
Prelude (Dock.hs)> right sample1
TwoListDock(1,2,3,4,<5>)
Prelude (Dock.hs)> right $ right sample1
TwoListDock(1,2,3,4,<5>)
Prelude (Dock.hs)> isLast $ right sample1
True
-}
-- ===========================================================
-- Ejercicio 8 (0.20 ptos.)
-- Elimina el objeto destacado. El destacado pasa a ser el anterior.
-- Si no hay anterior pasa a ser el siguiente.
-- Si queda vacía no hay destacado.
-- Error si está vacía

delete :: Dock a ->  Dock a
delete (D [] [] )    = error "Dock vacío"
delete (D (x:[]) (y:ys)) = D [y] ys
delete (D (x:xs) ys) = D xs ys


{-
Prelude (Dock.hs)> delete sample1
TwoListDock(1,2,<3>,5)
Prelude (Dock.hs)> delete $ delete sample1
TwoListDock(1,<2>,5)
Prelude (Dock.hs)> delete $ delete $ delete sample1
TwoListDock(<1>,5)
Prelude (Dock.hs)> delete $ delete $ delete $ delete sample1
TwoListDock(<5>)
Prelude (Dock.hs)> delete $ delete $ delete $ delete $ delete sample1
TwoListDock()
-}
-- ===========================================================
-- Ejercicio 9 (0.20 ptos.)
-- inserta el elemento a la izquierda del destacado y este elemento pasa a ser el destacado
-- Si el Dock esta vacía el elemento se convierte en el destacado

insertl :: a  ->  Dock a ->  Dock a
insertl n (D [] []) = D [n] []
insertl n (D (x:xs) ys ) = D (x:n:xs) ys


-- ===========================================================
-- Ejercicio 10  (0.10-0.20)
-- devuelve una lista con los elementos del Dock en el orden en el que están (del primero al último)
-- Implementar usando plegado de listas (0.20) otro metodo (0.10)

dockToList :: Dock a -> [a]
dockToList (D xs ys) = [lista | lista <- ((reverse xs) ++ ys)  ]


-- ===========================================================
-- Ejercicio 11 (0.10 ptos.)
-- genera un dock con los elementos de la lista. El destacado será el primero de la lista

listToDock :: [a] -> Dock a
listToDock xs = foldr (insertr) empty (reverse xs)
        where
            insertr n (D [] [] ) = D [n] []
            insertr n (D (x:[]) []) = D [x] [n] 
            insertr n (D (x:[]) (y:[])) = D [x] (y:n:[])
            insertr n (D xs (y:ys)) = D xs ((y:ys)++[n])


-- [4,3,2,1] [5]

{-
Prelude (Dock.hs)> listToDock [1..5]
TwoStackDock(<1>,2,3,4,5)
-}
-- =============================================================================
-- ========================= NO TOCAR DE AQUÍ PARA ABAJO =======================
instance (Show a) => Show (Dock a) where
    show d@(D si sd) | isEmpty d = "TwoListDock()"
    show d@(D si sd) = "TwoListDock("++(intercalate "," (reverse zs)) ++
                        (if (isFirst d) then "<" else ",<") ++ z ++(if (null sd) then ">" else ">,") ++ (intercalate "," ys)++ ")"
        where
          
          aux s
            | null s = []
            | otherwise = show x : aux s'
            where
                (x:s') = s          
          ys = aux sd
          (z:zs) = aux si
            
instance Arbitrary a => Arbitrary (Dock a) where
    arbitrary = do
            xs <- listOf arbitrary
            return (foldr insertl empty xs)
