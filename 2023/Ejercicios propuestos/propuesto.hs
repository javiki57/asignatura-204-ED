
type Token = (Int, Int)
f1 :: Token
f1 = (1,6)
f2 :: Token
f2 = (0,9)

-- Devuelve True si la ficha es válida
isValid :: Token -> Bool
isValid (a,b)
    | a >= 0 && a <= 6 = True
    | otherwise        = False


-- Devuelve True si las dos fichas son válidas
areEquals:: Token -> Token -> Bool 
areEquals (x1, y1) (x2, y2) = (x1 == x2 && y1 == y2) || (x1 == y2 && y1 == x2)


-- Devuelve True si la cadena es válida
isValidChain :: [Token] -> Bool 
isValidChain []  = True
isValidChain [x] = True
isValidChain (x:y:xs) = coincide x y && isValidChain (y:xs)
        where
            coincide (x1,y1) (x2,y2) = y1 == x2


-- Devuelve la cadena en la que se ha insertado la ficha (si es posible). En otro caso devuelve la cadena original
addTokenV1:: [Token] -> Token -> [Token]
addTokenV1 lista t
        | isValidChain (t:lista) = (t:lista)
        | otherwise = lista

addTokenV2:: [Token] -> Token -> [Token]
addTokenV2 lista t
        | isValidChain (lista ++ [t]) = lista ++ [t]
        | otherwise = lista

addTokenV3:: [Token] -> Token -> [Token]
addTokenV3 lista t
        | isValidChain (t:lista) = (t:lista)
        | isValidChain (lista ++ [t]) = lista ++ [t]
        | otherwise = lista

addTokenV4:: [Token] -> Token -> [Token]
addTokenV4 lista t@(x,y)
        | isValidChain (t:lista)       = (t:lista)
        | isValidChain (rt:lista)      = (rt:lista)
        | isValidChain (lista ++ [t])  = lista ++ [t]
        | isValidChain (lista ++ [rt]) = lista ++ [rt]
        | otherwise                    = lista
        
    where
        rt = (y,x)