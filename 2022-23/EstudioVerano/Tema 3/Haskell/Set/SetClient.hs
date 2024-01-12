------------------------------------------------------------
-- Estructuras de Datos
-- Tema 3. Estructuras de Datos Lineales
-- Pablo López
--
-- Módulo cliente del TAD Conjunto
------------------------------------------------------------

module SetClient where

import           Data.Char(toUpper)
import           Set

-- |
-- >>> mkSet [0,2..10]
-- Node 0 (Node 2 (Node 4 (Node 6 (Node 8 (Node 10 Empty)))))
-- mkSet "abracadabra"
-- Node 'a' (Node 'b' (Node 'c' (Node 'd' (Node 'r' Empty))))
mkSet :: Ord a => [a] -> Set a
mkSet xs = foldr insert empty xs

primos :: Set Integer
primos = mkSet [13, 7, 3, 2, 5, 11, 19]

-- |
-- >>> cardinal primos
-- 7
-- >>> cardinal (mkSet "abracadabra")
-- 5
{-
La función cardinal está tuneada, no sabía como contar los nodos
así que he añadido la función primerElemento en Set.hs y la he
puesto pública para utilizarla en SetClient.hs.
También he añadido Ord a a la función para que compilase. No está hecho
como se pedía pero está hecho.
-}
cardinal ::Ord a => Set a -> Integer
cardinal xs
        | isEmpty xs = 0
        | otherwise = 1 + cardinal (delete (primerElemento xs) xs)


-- |
-- >>> setToList primos
-- [2,3,5,7,11,13,19]

-- He hecho una función foldSet porque he querio para hacer esta función de fomar sencilla
setToList :: Set a -> [a]
setToList xs = foldSet (:) [] xs 


-- |
-- >>>  maximo primos
-- Just 19
-- >>> maximo (mkSet "abracadabra")
-- Just 'r'
-- maximo empty
-- Nothing
maximo :: Ord a => Set a -> Maybe a
maximo xs = ultimoElem (setToList xs)
        where
            ultimoElem xs = case reverse xs of
                      [] -> Nothing
                      (x:_) -> Just x
