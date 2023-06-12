-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module Seq (Seq (..),
    addSingleDigit
) where

data Seq a = Empty | Node a (Seq a) deriving (Eq, Show)

-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 1

addSingleDigit :: (Integral a) => a -> Seq a -> Seq a
addSingleDigit 0 seq = seq
addSingleDigit d seq 
    | acarreo == 0 = seq' 
    | otherwise    = Node acarreo seq'
    where 
        (acarreo,seq') = aux d seq
        aux d Empty = (d,Empty)
        aux d (Node x seq) = (nuevoAcarreo, Node d' seq')
            where
                (acarreo,seq') = aux d seq
                (nuevoAcarreo,d') = divMod (acarreo+x) 10
