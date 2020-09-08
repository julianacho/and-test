#WebAppTester: Probador de formularios online
#El programa utiliza Selenium para abrir un navegador web y ejecuta en �l, comandos escritos por el usuario.
#Descargue el controlador de Selenium para su navegador favorito (recuerde revisar la versi�n de su navegador) 
#desde https://www.selenium.dev/downloads/ y deje el controlador en la misma carpeta en que ejecutar� el programa.
#Cada l�nea que comienza con #, no se tiene en cuenta y ser� tratada como comentario.
#El programa lee la carpeta "Acciones" y con todas las carpetas en su interior arma un �rbol.
#El �rbol se compone de carpetas y de acciones representadas en cada uno de los archivos con extension .txt en cada carpeta
#Si se encuentra un archivo inicio.txt en una carpeta, se ejecutar� ese archivo antes de ejecutar cualquier otra acci�n en la carpeta.
#Si se encuentra un archivo fin.txt en una carpeta, se ejecutar� ese archivo despu�s de ejecutar cualquier acci�n en la carpeta.
#Ejemplo de estructura
#[Acciones]
#Acciones/inicio.txt
#Acciones/fin.txt
#[Acciones/administracion]
#Acciones/administracion/inicio.txt
#Acciones/administracion/crearpregunta.txt
#Acciones/administracion/listarpreguntas.txt
#
#Si desde el programa se solicita la ejecuci�n del comando "Acciones/administracion/listarpreguntas", el programa ejecutar�
#los siguientes comandos en orden:
#Acciones/inicio.txt
#Acciones/administracion/inicio.txt
#Acciones/administracion/listarpreguntas.txt
#Acciones/fin.txt
#
#Los archivos inicio.txt y fin.txt no son obligatorios, son solo una forma de colocar acciones como autenticarse antes de entrar a una 
#p�gina en la cual se ejecutar� un comando o cerrar sesi�n despu�s de salir de cierta p�gina
#
#Cada acci�n es un conjunto de instrucciones que ser�n ejecutadas por el navegador de internet.
#Cada instrucci�n tiene el formato "acci�n={comando}"
#Descargue el programa ya compilado desde https://drive.google.com/open?id=1p8EWeIFzNU6YNwwH0PtU5ym7FV44aVds
#JRE <= 1.8
#[Acciones soportadas]

#ir - Abre una p�gina internet. 
#Ejemplo:
ir={https://duckduckgo.com/}

#esperar - Espera a que la p�gina cargue completamente despues de un cambio de URL o despues de hacer clic en algun lado.
#esperar� a que cierto objeto (dado su selector), aparezca o simplemente a que la p�gina cargue completa
#Ejemplo:
esperar={}

#escribir - Escribe texto en un componente de la pagina dado un selector css.
#Ejemplo:
escribir={
 "selector":"#search_form_input_homepage",
 "texto":"inicio"
}

#clic - Hace clic en un componente de la pagina dado un selector css.
#Ejemplo:
clic={ 
  "selector":"#search_button_homepage"
}

#esperar - Espera a que la p�gina cargue completamente despues de un cambio de URL o despues de hacer clic en algun lado.
#esperar� a que cierto objeto (dado su selector), aparezca o simplemente a que la p�gina cargue completa
#Ejemplo:
esperar={
  "selector":"#duckbar"
}

#doble clic - Hace doble clic en algun elemento de la pagina, dado su selector.
#Ejemplo:
doble clic={
    "selector": "body"
}

#clic derecho - Hace clic derecho en cualquier elemento de la pagina, dado su selector.
#Ejemplo:
clic derecho={
    "selector": "body"
}

#Cuando ejecute cualquier comando en esta carpeta, tambi�n se ejecutar� este archivo.
#
#Referencias de Selectores CSS: 
# https://w3.org/wiki/CSS_/_Selectores_CSS
# https://www.w3schools.com/cssref/css_selectors.asp