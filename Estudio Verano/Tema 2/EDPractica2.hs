-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 2
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module Practica2 where

import           Data.List      -- (nub, (\\))
import           Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 1 - 
-------------------------------------------------------------------------------

data Direction = North | South | East | West
                            deriving (Eq,Ord,Enum,Show)

(<<) :: Direction -> Direction -> Bool
a << b = fromEnum a < fromEnum b

p_menor x y = (x < y) == (x << y)
instance Arbitrary Direction where
        arbitrary = do
            n <- choose (0,3)
            return $ toEnum n


-------------------------------------------------------------------------------
-- Ejercicio 2 - 
-------------------------------------------------------------------------------

máximoYresto :: Ord a => [a] -> (a,[a])
máximoYresto []     = error "Lista vacía"
máximoYresto (x:xs) = (maximo, filter (/= maximo) (x:xs))
    where
        maximo = maximum (x:xs)


máximoYresto' :: Ord a => [a] -> (a,[a])
máximoYresto' []     = error "Lista vacía"
máximoYresto' (x:xs) = (maximo, sort (filter (/= maximo) (x:xs)))
    where
        maximo = maximum (x:xs)


-------------------------------------------------------------------------------
-- Ejercicio 4 - distintos
-------------------------------------------------------------------------------

distintos :: Eq a => [a] -> Bool
distintos []  = True
distintos (x:xs) =  x `notElem` xs && distintos xs

-------------------------------------------------------------------------------
-- Ejercicio 3 - reparte
-------------------------------------------------------------------------------

reparte :: [a] -> ([a], [a])
reparte []     = ([],[])
reparte [x]    = ([x],[])
reparte (x:y:xs) = (x:parte1 , y:parte2)
    where
        (parte1, parte2) = reparte xs


-------------------------------------------------------------------------------
-- Ejercicio 5 --
-------------------------------------------------------------------------------

replicate' :: Int -> a -> [a]
replicate' n x = [ x | _ <- [1..n]]



-------------------------------------------------------------------------------
-- Ejercicio 6 --
-------------------------------------------------------------------------------

divisores :: Int -> [Int]
divisores x = [n | n <- [1..abs(x)], divideA x n]
    where
        divideA x n = mod x n == 0

divisores' :: Int -> [Int]
divisores' x = [ n | n <- [-abs(x)..abs(x)], divideA x n]
    where
            divideA x n = n /=0 && mod x n == 0



-------------------------------------------------------------------------------
-- Ejercicio 7 --
-------------------------------------------------------------------------------

mcd :: Int -> Int -> Int
mcd x y =  maximum divisoresjuntos
    where
        divisoresjuntos = [ n | n <- divisores' x , t <- divisores' y , n==t]


mcm :: Int -> Int -> Int
mcm x y = div (x*y) (mcd x y) 

        
-------------------------------------------------------------------------------
-- Ejercicio [empareja] de la lista de ejercicios extra
-------------------------------------------------------------------------------

-- Hoogle (https://www.haskell.org/hoogle/) es un buscador para el API de Haskell.
-- Usa Hoogle para encontrar información sobre la función 'zip'.

empareja :: [a] -> [b] -> [(a, b)] -- predefinida como zip
empareja xs ys = [(x,y) | (x,y) <- juntar xs ys]
    where
        juntar [] _          = []
        juntar _ []          = []
        juntar (x:xs) (y:ys) = (x,y) : juntar xs ys


emparejaCon :: (a -> b -> c) -> [a] -> [b] -> [c]
emparejaCon f [] _ = []
emparejaCon f _ [] = []
emparejaCon f (x:xs) (y:ys) = f x y : emparejaCon f xs ys


-------------------------------------------------------------------------------
-- Ejercicio 8 --
-------------------------------------------------------------------------------
esPrimo :: Int -> Bool
esPrimo x = funcion x
    where
        divi = divisores x
        funcion x
            | divi == [1,x] = True
            | otherwise  = False


-------------------------------------------------------------------------------
-- Ejercicio 13 - desconocida
-------------------------------------------------------------------------------

-- Usa Hoogle para consultar la función 'and'.

desconocida :: Ord a => [a] -> Bool
desconocida xs = and [ x <= y | (x, y) <- zip xs (tail xs) ]

-------------------------------------------------------------------------------
-- Ejercicio 14 - inserta y ordena
-------------------------------------------------------------------------------

-- Usa Hoogle para consultar las funciones 'takeWhile' y 'dropWhile'.

-- 14.a - usando takeWhile y dropWhile
inserta :: untyped
inserta = undefined

-- 14.b - mediante recursividad
insertaRec :: untyped
insertaRec = undefined

-- 14.c

-- Las líneas de abajo no compilan hasta que completes los apartados
-- anteriores. Cuando los completes, elimina los guiones del comentario
-- y comprueba tus funciones.

-- prop_inserta :: Ord a => a -> [a] -> Property
-- prop_inserta x xs = desconocida xs ==> desconocida (inserta x xs)

-- prop_insertaRec :: Ord a => a -> [a] -> Property
-- prop_insertaRec x xs = desconocida xs ==> desconocida (insertaRec x xs)

-- 14.e - usando foldr
ordena :: untyped
ordena = undefined

-- Para definir prop_ordena_OK tendrás que usar el operador sobre listas (\\).
-- Consulta Hoogle.

-- 14.f
prop_ordena_OK :: untyped
prop_ordena_OK = undefined

-------------------------------------------------------------------------------
-- Ejercicio [mezcla] de la lista de ejercicios extra
-------------------------------------------------------------------------------

mezcla :: Ord a => [a] -> [a] -> [a]
mezcla  ys = undefined

-------------------------------------------------------------------------------
-- Ejercicio [pares] de la lista de ejercicios extra
-------------------------------------------------------------------------------

cotizacion :: [(String, Double)]
cotizacion = [("apple", 116), ("intel", 35), ("google", 824), ("nvidia", 67)]

buscarRec :: Eq a => a -> [(a,b)] -> [b]
buscarRec x ys = undefined

buscarC :: Eq a => a -> [(a, b)] -> [b]
buscarC x ys = undefined

buscarP :: Eq a => a -> [(a, b)] -> [b]
buscarP x ys = undefined

prop_buscar_OK :: (Eq a, Eq b) => a -> [(a, b)] -> Bool
prop_buscar_OK x ys = undefined

{-

Responde las siguientes preguntas si falla la propiedad anterior.

¿Por qué falla la propiedad prop_buscar_OK?

Realiza las modificaciones necesarias para que se verifique la propiedad.

-}

valorCartera :: [(String, Double)] -> [(String, Double)] -> Double
valorCartera cartera mercado = undefined

-------------------------------------------------------------------------------
-- Ejercicio 12 - concat
-------------------------------------------------------------------------------

concatP :: [[a]] -> [a]
concatP xss = undefined

concatC :: [[a]] -> [a]
concatC xss = undefined
