------------------------------------------------------------
-- Estructuras de Datos
-- Tema 3. Estructuras de Datos Lineales
-- Pablo López
--
-- Módulo del TAD Pila
------------------------------------------------------------

------------------------------------------------------------
-- Implementación del TAD Pila
------------------------------------------------------------

module Stack(Stack, --Lista de exportación, lo que esta es publico y lo que no es privado.
            empty,
            push,
            top,
            pop,
            isEmpty) where

import           Test.QuickCheck

{-
   Recuerda que una lista de `n` elementos es una cabeza seguida de
   una lista de `n-1` elementos:

                              lista de 2 elementos
                 cabeza       /
                      \   --------
                       3: 8: 6: []
                       -----------
                            \
                            lista de 3 elementos

   La lista vacía es la lista más pequeña que puede construirse.

   Haskell define dos constructores para listas:

           []  :: [a]
           (:) :: a -> [a] -> [a]
                 /      \      \
             cabeza    cola    nueva lista

   En realidad, Haskell define el tipo algebraico parametrizado y **recursivo**
   `[a]` con dos **constructores de datos** para **representar** listas:

                 tipo base de la lista
                      /
                     /    caso base
                    /    /
           data [] a = []             -- :: [a]
               /     | (:) a [a]      -- :: a -> [a] -> [a]
              /                \
             /                  caso recursivo
      nombre del tipo (lista)

   Observa ahora la siguiente pila:

                                 cima
      pila con 3 elementos     /
                          \ | 7    pila con 2 elementos
                            | 1 |/
                            | 5 |

  Una pila de 3 elementos es una cima seguida de una pila de 2 elementos.
  La pila más pequeña posible es la pila vacía.

  Por lo tanto, una pila puede ser:

      - la pila vacía (caso base)
      - una cima seguida de una pila (caso recursivo)

  Podemos representar las pilas en Haskell mediante un tipo algebraico parametrizado
  y recursivo `Stack a`. Necesitaremos dos constructores de datos para:

      - la pila vacía (caso base)
      - la pila formada por una cima seguida del resto de la pila (caso recursivo)

-}

-- El tipo algebraico `Stack a` es la representación física de la pila.
-- Esta representación debería estar **oculta** para que el tipo `Stack a` sea un TAD.

data Stack a = Empty -- vacia :: Stack a
            | Node a (Stack a)  -- "a" cima seguida de pila "Stack a"
            deriving Show

-- Ejercicio: representa una pila `customers` en la que aparezcan de la cima
-- a la base los elementos "peter", "mary" y "john".

customers :: Stack String
customers = Node "peter" (Node "mary" (Node "john" Empty))

-- Complejidad: O(1)
-- |
-- >>> empty
-- Empty
empty :: Stack a
empty = Empty

-- Complejidad: O(?)
-- |
-- | push "frank" customers
-- Node "frank" (Node "peter" (Node "mary" (Node "john" Empty)))
push :: a -> Stack a -> Stack a
push x s = Node x s

-- Complejidad: O(1)
-- |
-- >>> pop customers
-- Node "mary" (Node "john" Empty)
pop :: Stack a -> Stack a
pop Empty = error "pop: pila vacía"
pop (Node _ s) = s

-- Complejidad: O(1)
-- |
-- >>> top customers
-- "peter"
top :: Stack a -> a
top Empty= error "pop: pila vacía"
top (Node x _) = x

-- Complejidad: O(1)
-- |
-- >>> isEmpty empty
-- True
--
-- isEmpty customers
-- False
isEmpty :: Stack a -> Bool
isEmpty Empty = True
isEmpty _ = False

{-
   La siguiente instancia de `Arbitrary` es para enseñar a QuickCheck
   a generar `Stack` aleatorias. No hay que saber cómo hacerlo;
   siempre se facilita.
-}

instance Arbitrary a => Arbitrary (Stack a) where
  arbitrary =  do
                xs <- listOf arbitrary
                return (foldr push empty xs)
