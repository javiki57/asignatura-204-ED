------------------------------------------------------------------
--Parcial 2022 Haskell
--Polynomial
------------------------------------------------------------------
module Polynomial(
    Pol
    ,empty
    ,grade
    ,coeff
    ,insert
    ,remove
    ,list2Pol
    ,sumP
    ,foldPol
    ,eval
)where
    --propiedad p1_suma quickCheck

import Test.QuickCheck

type Grade = Int
type Coefficient = Integer

data Pol = Nil | P Grade Coefficient (Pol) deriving Show
--un polinomio se almacena como una secuencia de terminos ordenados de mayor a menor grado
--Invariantes : Solo se almacenan los terminos con coeficiente distinto de 0
--      Todos los terminos tineen diferente grado

--Por ejemplo
--p1 = x^3 + 2x^2 + 8 se representa
p1 = P 3 1 (P 2 2 (P 0 8 Nil))
--p2 = x^5 + 8x
p2 = P 5 2 (P 1 8 Nil)
p3 = P 3 (-1) (P 2 2 (P 0 (-8) Nil))

--Nil representa un polinomio sin terminos (vacio)

--Ejercicio 1: Devuelve un polinomio vacio
empty :: Pol
empty = Nil

--Ejercicio 2: Devuelve el grado maximo del polinomio
grade:: Pol -> Grade
grade (P g c p) = g

--Ejercicio 3: Devuelve el coeficiente del termino de grado g
coeff :: Grade -> Pol -> Coefficient
coeff g (P n c p)
        | g == n    = c
        | g < n     = coeff g p
        | otherwise = error "El polinomio no tiene ese grado"

--Ejercicio 4: Añade el termino de grado g con coeficiente c al polinomio.
--Si el polinomio ya tenia un termino de ese grado, actualiza el coeficiente,
-- es decir reemplaza el antiguo coeficiente por el nuevo
insert:: Grade -> Coefficient -> Pol -> Pol
insert g 0 p = p
insert g c Nil = P g c Nil
insert g c s@(P n c2 p)
        | g == n    = (P g c p)
        | g < n     = (P n c2 (insert g c p))
        | otherwise = (P g c s)

insert' :: Grade -> Coefficient -> Pol -> Pol
insert' g 0 p = p
insert' g c Nil = P g c Nil
insert' g c s@(P g2 c2 p)
        | g == g2   = P g (c+c2) p
        | g < g2    = P g2 c2 (insert g c p)
        | otherwise = s


--Ejercicio 5: Elimina el termino g del polinomio
--Si el polinomio no tenia termino de grado g no hace nada
remove :: Grade -> Pol -> Pol
remove g Nil = Nil
remove g (P n c p)
        | g == n    = p
        | g < n     = (P n c (remove g p))
        | otherwise = P n c p


--Ejercicio 6: Dada una lista con los coeficientes de los terminos de un polinomio,
--donde la posicion indica el grado, devuelve el Pol correspondiente
--Si la lista tinen un coeficiente 0 ese termino no se almacena en la lista.
--Por ejemplo, (x^3 + 2x^2 +8) se representa con la lista [8,0,2,1]
--y usando list2Pol se obtiene el polinomio P 3 1 (P 2 2 (P 0 8 Nil))
list2Pol :: [Integer] -> Pol
list2Pol lista = foldr (\(c,g) p -> insert g c p) empty $ zip lista [0..]



--Ejercicio 7: suma dos polinomios que pueden tener diferente grado
sumP :: Pol -> Pol -> Pol
sumP Nil p2 = p2
sumP p1 Nil = p1
sumP (P g1 c1 p1) (P g2 c2 p2)
        | g1 == g2  = insert g1 (c1+c2) (sumP p1 p2)
        | g1 < g2   = (P g2 c2 (sumP (P g1 c1 p1) p2))
        | otherwise = (P g1 c1 (sumP p1 (P g2 c2 p2)))

--Ejercicio 8: plegado a la derecha de polinomios
foldPol :: (Grade -> Coefficient -> b -> b) -> b -> Pol -> b
foldPol f z Nil = z
foldPol f z (P g c p) = f g c (foldPol f z p)


--Ejercicio 9: evaluacion del polinomio
--por ejemplo, si tenemos el polinomio x^2 + 1 representado como
--p1 = P 2 1 (P 0 1 Nil) eval 3 p1 devuelve el resultado de evaluar el polinomio
--en 3 (3^2 + 1 = 10)
eval:: Integer -> Pol -> Integer
eval n Nil = 0
eval n (P g c p) = c * (n^g) + (eval n p)

eval' :: Integer -> Pol -> Integer
eval' n t@(P g c p) = foldPol (\g1 c1 s -> c1*(n^g1) + s) 0 t


--Ejercicio 10 : Establece una propiedad quickCheck que compruebe que la suma de
--dos polinomios p1 y p2 tiene el mismo grado que el polinomio de mayor grado

p1_suma :: Pol -> Pol -> Bool
p1_suma p1 p2 =
  let sumP1P2 = sumP p1 p2  -- Calcula la suma de p1 y p2
      maxGradeP1 = grade p1  -- Grado de p1
      maxGradeP2 = grade p2  -- Grado de p2
      maxGradeSum = grade sumP1P2  -- Grado de la suma
  in maxGradeSum == max maxGradeP1 maxGradeP2





-------------------------------------------------------------------------
-------------------------------------------------------------------------
---------------------NO TOCAR DESDE AQUI---------------------------------
-------------------------------------------------------------------------
-------------------------------------------------------------------------
completa :: (Int, Integer) -> (Pol, Bool) -> (Pol, Bool)
completa (g,c) (p, b)
    |b = (insert g c p, False)
    |otherwise = (remove g p, False)

instance Arbitrary Pol where
    arbitrary = do
        gs <- listOf (arbitrary:: Gen Int)
        cs <- listOf (arbitrary:: Gen Integer)
        return (fst (foldr completa (Nil, True) (map(\(g,c) -> (abs g, c)) $ filter (\(g,c) -> c/=0)(zip gs cs))))
