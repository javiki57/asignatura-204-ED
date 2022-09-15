-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
-- (completa y sustituye los siguientes datos)
-- Titulación: Grado en Ingeniería .......................................... [Informática | del Software | de Computadores].
-- Alumno: QUIRANTE PEREZ, JAVIER
-- Fecha de entrega: DIA | MES | AÑO
--
-- Relación de Ejercicios 1. Ejercicios resueltos: ..........
--
-------------------------------------------------------------------------------
--import Test.QuickCheck

--Ejercicio 1
-- Tres enteros positivos x, y, z constituyen una terna pitagórica si x^2 +y^2 =z^2, es decir, si son los lados de un triángulo rectángulo.
-- a) Define la función esTerna :: Integer -> Integer -> Integer -> Bool
-- que compruebe si tres valores forman una terna pitagórica. Por ejemplo:
-- Main> esTerna 3 4 5 Main> esTerna 3 4 6
--          True                False

esTerna :: Integer -> Integer -> Integer -> Bool
esTerna x y z | x*x + y*y == z*z = True
              | otherwise = False

-- b) Es fácil demostrar que para cualesquiera x e y enteros positivos con x>y, la terna (x^2 -y^2, 2xy, x^2 +y^2 ) es pitagórica.
-- Usando esto, escribe una función terna que tome dos parámetros y devuelva una terna pitagórica. Por ejemplo:
-- Main> terna 3 1
-- (8,6,10)
-- Main> esTerna 8 6 10
-- True
terna :: Integer -> Integer -> (Integer, Integer, Integer)
terna x y | x > y = (x*x - y*y, 2*x*y, x*x + y*y)
          | otherwise = error "x no es menor que y"