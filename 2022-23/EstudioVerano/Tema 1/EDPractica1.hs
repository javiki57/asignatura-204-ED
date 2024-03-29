-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 1
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module EDPractica1 where

import           Test.QuickCheck

--Ejercicio 1
-- Tres enteros positivos x, y, z constituyen una terna pitagórica si x^2 +y^2 =z^2, es decir, si son los lados de un triángulo rectángulo.
-- a) Define la función esTerna :: Integer -> Integer -> Integer -> Bool
-- que compruebe si tres valores forman una terna pitagórica. Por ejemplo:
-- Main> esTerna 3 4 5 Main> esTerna 3 4 6
--          True                False

esTerna :: Integer -> Integer -> Integer -> Bool
esTerna x y z
      | x < 0 || y < 0 || z < 0 = error "Valores negativos"
      | x*x + y*y == z*z = True
      | otherwise        = False

terna :: Integer -> Integer -> (Integer, Integer, Integer)
terna x y
      | x < 0 || y < 0 = error "Valores negativos"
      | x > y          = ((x*x)-(y*y), 2*x*y, (x*x)+(y*y))
      | otherwise      = error "x no es mayor que y"


p_ternas x y = x>0 && y>0 && x>y ==> esTerna l1 l2 h
      where
            (l1,l2,h) = terna x y
-------------------------------------------------------------------------------
-- Ejercicio 2 - intercambia
-------------------------------------------------------------------------------

intercambia :: (a,b) -> (b,a)
intercambia (x,y) = (y,x)

-------------------------------------------------------------------------------
-- Ejercicio 3 - ordena2 y ordena3
-------------------------------------------------------------------------------

-- 3.a
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x,y)
      | x <= y    = (x,y)
      | otherwise = (y,x)

p1_ordena2 x y = enOrden (ordena2 (x,y))
   where enOrden (x,y) = x <= y

p2_ordena2 x y = mismosElementos (x,y) (ordena2 (x,y))
   where
      mismosElementos (x,y) (x',y') =
           (x == x' && y == y') || (x == y' && y == x')

-- 3.b
ordena3 :: Ord a => (a,a,a) -> (a,a,a)
ordena3 (x,y,z)
      | x > y    = ordena3 (y,x,z)
      | y > z    = ordena3 (x,z,y)
      |otherwise = (x,y,z)

-- 3.c
p1_ordena3 x y z = enOrden (ordena3 (x,y,z))
      where enOrden (x,y,z) = x <= y && y <= z

p2_ordena3 x y z = mismosElementos (x,y,z) (ordena3 (x,y,z)) 
      where mismosElementos (x,y,z) (x',y',z') = (x == x' && y == y' && z == z') || (x == y' && y == z' && z == x')

-------------------------------------------------------------------------------
-- Ejercicio 4 - max2
-------------------------------------------------------------------------------

-- 4.a
max2 :: Ord a => a -> a -> a
max2 x y
      | x > y     = x
      | otherwise = y

-- 4.b
-- p1_max2: el máximo de dos números x e y coincide o bien con x o bien con y.

p1_max2 x y = max2 x y == x || max2 x y == y 
-- p2_max2: el máximo de x e y es mayor o igual que x y mayor o igual que y.

p2_max2 x y = max2 x y >= x || max2 x y >= y -- completar

-- p3_max2: si x es mayor o igual que y, entonces el máximo de x e y es x.

p3_max2 x y = max2 x y >= y ==> max2 x y == x -- completar

-- p4_max2: si y es mayor o igual que x, entonces el máximo de x e y es y.

p4_max2 x y = y >= x ==> max2 x y == y  -- completar

-------------------------------------------------------------------------------
-- Ejercicio 5 - entre (resuelto, se usa en el ejercicio 7)
-------------------------------------------------------------------------------

entre :: Ord a => a -> (a, a) -> Bool
entre x (inf,sup) = inf <= x && x <= sup

-------------------------------------------------------------------------------
-- Ejercicio 6 --
-------------------------------------------------------------------------------
iguales3 :: Eq a => (a,a,a) -> Bool
iguales3 (x,y,z) = x == y && y == z

-------------------------------------------------------------------------------
-- Ejercicio 7 - descomponer
-------------------------------------------------------------------------------

-- Para este ejercicio nos interesa utilizar la función predefinida:
--
--              divMod :: Integral a => a -> a -> (a, a)
--
-- que calcula simultáneamente el cociente y el resto de la división entera:
--
--   *Main> divMod 30 7
--   (4,2)

-- 7.a
type TotalSegundos = Integer
type Horas         = Integer
type Minutos       = Integer
type Segundos      = Integer

descomponer :: TotalSegundos -> (Horas,Minutos,Segundos)
descomponer x = (h,m,s)
      where
            (h,m') = divMod x 3600
            (m,s)  = divMod m' 60
-- 7.b
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                           && m `entre` (0,59)
                           && s `entre` (0,59)
          where (h,m,s) = descomponer x


-------------------------------------------------------------------------------
-- Ejercicio 8 - 
-------------------------------------------------------------------------------

unEuro :: Double
unEuro = 166.386

pesetasAEuros :: Double -> Double
pesetasAEuros x = x / unEuro

eurosAPesetas :: Double -> Double
eurosAPesetas x = x * unEuro

-------------------------------------------------------------------------------
-- Ejercicio 9 - 
-------------------------------------------------------------------------------

infix 4 ~=
(~=) :: Double -> Double -> Bool
x ~= y = abs (x-y) < epsilon
      where epsilon = 1/1000


-------------------------------------------------------------------------------
-- Ejercicio 10 - 
-------------------------------------------------------------------------------

raices :: Double -> Double -> Double -> (Double, Double)
raices a b c
      | raiz < 0  = error "Raices no reales"
      | otherwise = (x,y)
      where
            raiz = b*b-4*a*c
            x = (-b + sqrt(raiz))/2*a
            y = (-b - sqrt(raiz))/2*a


-------------------------------------------------------------------------------
-- Ejercicio 11 - 
-------------------------------------------------------------------------------

esMúltiplo :: Integer -> Integer -> Bool
esMúltiplo x y = x `mod` y == 0

-------------------------------------------------------------------------------
-- Ejercicio 12 - 
-------------------------------------------------------------------------------

(==>>) :: Bool -> Bool -> Bool
False ==>> _ = True
True ==>> y  = y

-------------------------------------------------------------------------------
-- Ejercicio 13 - 
-------------------------------------------------------------------------------

esBisiesto :: Integer -> Bool
esBisiesto x
      | x `mod` 4 == 0                       = True
      | x `mod` 100 == 0 && x `mod` 400 == 0 = True
      | otherwise                            = False

-------------------------------------------------------------------------------
-- Ejercicio 14 - potencia
-------------------------------------------------------------------------------

-- 14.a
potencia :: Integer -> Integer -> Integer
potencia b 0 = 1
potencia b n = b * potencia b (n-1)

-- 14.b
potencia' :: Integer -> Integer -> Integer
potencia' b n
      | even n    = potencia (potencia b (n `div` 2)) 2
      | otherwise = b * potencia (potencia b ((n-1) `div` 2)) 2

-- 14.c
p_pot b n =
   potencia b m == sol && potencia' b m == sol
   where sol = b^m
         m = abs n
-- 14.d
{-

Escribe en este comentario tu razonamiento:

-}

-------------------------------------------------------------------------------
-- Ejercicio 15 -
-------------------------------------------------------------------------------

factorial :: Integer -> Integer
factorial 0 = 1
factorial n = n * factorial (n-1)

-------------------------------------------------------------------------------
-- Ejercicio 16 -
-------------------------------------------------------------------------------

divideA :: Integer -> Integer -> Bool
divideA x y = y `mod` x == 0

p1_divideA x y = y/=0 && y `divideA` x ==> div x y * y == x

-------------------------------------------------------------------------------
-- Ejercicio 17 -
-------------------------------------------------------------------------------

mediana :: Ord a => (a,a,a,a,a) -> a
mediana (x,y,z,t,u)
      | x > z             = mediana (z,y,x,t,u)
      | y > z             = mediana (x,z,y,t,u)
      | z > t             = mediana (x,y,t,z,u)
      | z > u             = mediana (x,y,u,t,z)
      | otherwise         = z