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

primosHasta :: Int -> [Int]
primosHasta x = [ lista | lista <- [2..x] , esPrimo lista]

primosHasta' :: Int -> [Int]
primosHasta' x = filter (esPrimo) [1..x] 

-------------------------------------------------------------------------------
-- Ejercicio 9 --
-------------------------------------------------------------------------------
pares :: Int -> [(Int,Int)]
pares n = filter (\(x,y) -> x <= y) [ (x,y) | x <- primosHasta n, y <- primosHasta n,  x+y == n ]

goldbach :: Int -> Bool
goldbach n = n >= 2 &&  not(null (pares n))

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
inserta :: Ord a => a -> [a] -> [a]
inserta x xs = takeWhile (<=x) xs ++ [x] ++ dropWhile (<=x) xs

-- 14.b - mediante recursividad
insertaRec :: Ord a => a -> [a] -> [a]
insertaRec x []     = [x]
insertaRec x (y:ys)
            | x <= y = (x:y:ys)
            | otherwise = y : insertaRec x ys

-- 14.c

-- Las líneas de abajo no compilan hasta que completes los apartados
-- anteriores. Cuando los completes, elimina los guiones del comentario
-- y comprueba tus funciones.

prop_inserta :: Ord a => a -> [a] -> Property
prop_inserta x xs = desconocida xs ==> desconocida (inserta x xs)

prop_insertaRec :: Ord a => a -> [a] -> Property
prop_insertaRec x xs = desconocida xs ==> desconocida (insertaRec x xs)

-- 14.e - usando foldr
ordena :: Ord a => [a] -> [a] 
ordena xs = foldr inserta [] xs


-------------------------------------------------------------------------------
-- Ejercicio [mezcla] de la lista de ejercicios extra
-------------------------------------------------------------------------------

mezcla :: Ord a => [a] -> [a] -> [a]
mezcla [] ys = ys
mezcla xs [] = xs
mezcla (x:xs) (y:ys)
            | x <= y    = x : mezcla xs (y:ys)
            | otherwise = y : mezcla (x:xs) ys


cotizacion :: [(String, Double)]
cotizacion = [("apple", 116), ("intel", 35), ("google", 824), ("nvidia", 67)]

buscarRec :: Eq a => a -> [(a,b)] -> [b]
buscarRec x [] = []
buscarRec x ((k,v):xs)
            | k == x    = [v]
            | otherwise = buscarRec x xs


buscarC :: Eq a => a -> [(a, b)] -> [b]
buscarC x ys = [ v | (k,v) <- ys, k == x ]

buscarP :: Eq a => a -> [(a, b)] -> [b]
buscarP x ys = foldr f [] ys
    where
        f cab solCola
                | x == fst cab = [snd cab]
                | otherwise    = solCola
        
                
prop_buscar_OK :: (Eq a, Eq b) => a -> [(a, b)] -> Bool
prop_buscar_OK x ys = buscarC x ys == buscarP x ys

{-

Responde las siguientes preguntas si falla la propiedad anterior.

¿Por qué falla la propiedad prop_buscar_OK?

Realiza las modificaciones necesarias para que se verifique la propiedad.

-}

valorCartera :: [(String, Double)] -> [(String, Double)] -> Double
valorCartera cartera cotizacion = sum [cantidad * valor | (titulo, cantidad) <- cartera, Just valor <- [lookup titulo cotizacion]]
-------------------------------------------------------------------------------
-- Ejercicio 12 - concat
-------------------------------------------------------------------------------

concatP :: [[a]] -> [a]
concatP [] = []
concatP[[x]] = [x]
concatP (x:xs) = x ++ concatP xs

concat' :: [[a]] -> [a]
concat' xss = foldr (++) [] xss

concatC :: [[a]] -> [a]
concatC xss = [ x | xs <- xss, x <- xs]
