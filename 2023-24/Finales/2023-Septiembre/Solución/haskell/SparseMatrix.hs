-------------------------------------------------------------------------------
-- Student's name: ?????????????????????????????????????
-- Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
-- Student's group: ?
-- PC code: ???
--
-- Data Structures. Grado en Informatica. UMA.
-------------------------------------------------------------------------------

module SparseMatrix(SparseMatrix, set, get, add, transpose, fromList) where

import qualified DataStructures.Dictionary.AVLDictionary as D 

data Index = Idx Int Int deriving (Eq, Ord, Show)

-- | = Exercise a - sparseMatrix
data SparseMatrix = SM Int Int (D.Dictionary Index Int) deriving Show 

sparseMatrix :: Int -> Int -> SparseMatrix
sparseMatrix f c 
    | f > 0 && c > 0 = SM f c D.empty
    | otherwise      = error "Dimensiones no válidas"

-- | = Exercise b - value
value :: SparseMatrix -> Index -> Int
value (SM f c dic) idx = case D.valueOf idx dic of
    Nothing -> 0
    Just x  -> x

-- | = Exercise c - update
update :: SparseMatrix -> Index -> Int -> SparseMatrix
update (SM f c dic) idx v
        | v == 0    = SM f c (D.delete idx dic)
        | otherwise = SM f c (D.insert idx v dic)

-- | = Exercise d - index
index :: SparseMatrix -> Int -> Int -> Index
index (SM f c dic) fil col
        | fil <= f && col <= c && fil >= 0 && col >= 0 = Idx fil col
        | otherwise                                    = error "Coordenadas no válidas"

-- | = Exercise e - set
set :: SparseMatrix -> Int -> Int -> Int -> SparseMatrix
set sm fil col v = update sm (index sm fil col) v

-- | = Exercise f - get
get :: SparseMatrix -> Int -> Int -> Int
get sm fil col = value sm (index sm fil col)

-- | = Exercise g - add
add :: SparseMatrix -> SparseMatrix -> SparseMatrix
add (SM f1 c1 dic1) (SM f2 c2 dic2)
        | f1 /= f2 && c1 /= c2 = error "Dimensiones diferentes"
        | otherwise = suma (SM f1 c1 dic1) (D.keysValues dic2)
            where
                suma sm@(SM f c dic1) [] = sm
                suma sm@(SM f c dic) ((x,y):xs)
                        | not(D.isDefinedAt x dic) = suma (update sm x y) xs
                        | otherwise                = suma (update sm x (y + value sm x)) xs
                            
                                


-- | = Exercise h - transpose
transpose :: SparseMatrix -> SparseMatrix
transpose (SM f c dic) = let sm = sparseMatrix c f in actualizar sm (D.keysValues dic)
    where
        actualizar sm [] = sm
        actualizar sm@(SM f c dic) (((Idx fil col),b):xs) = actualizar (update sm (index sm col fil) b) xs



--SM 2 2 AVLDictionary(Idx 0 2->10,Idx 1 0->20)
--SM 2 2 AVLDictionary(Idx 0 1->20,Idx 2 0->10)

{-
0 0 10 0
20 0 0 0
0 0 0 0

0 20 0 0
0  0 0 0
10 0 0 0
 0 0 0 0
-}


-- | = Exercise i - fromList
-- Complexity of fromList: ???? -- to complete
fromList :: Int -> Int -> [Int] -> SparseMatrix
fromList f c ls
    | mod (length ls) 3 /= 0 = error "La lista no es múltiplo de 3"
    | otherwise              = crear (sparseMatrix f c) ls
        where
            crear sm []         = sm
            crear sm (x:y:z:xs) = crear (update sm (index sm x y) z) xs




-- Examples

m1 :: SparseMatrix
m1 = fromList 5 5 [0,1,11, 3,0,30, 3,1,31]

m2 :: SparseMatrix
m2 = fromList 5 5 [0,1,-11, 3,0,30, 4,1,41]

m3 :: SparseMatrix 
m3 = add m1 m2

m4 :: SparseMatrix
m4 = transpose m3