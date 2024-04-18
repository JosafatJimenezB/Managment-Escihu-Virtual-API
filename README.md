# Software de Gestión Escolar - Escihu Virtual API 

## Acerca del proyecto
En la Escuela Superior de Ciencias y Humanidades (ESCIHU), se utiliza actualmente un sistema de control escolar proporcionado por un prestador de servicios, aunque no satisface completamente las necesidades debido a su naturaleza de Software como Servicio (SAAS). Por esta razón, se ha decidido desarrollar un software más especializado que se adapte mejor a las necesidades específicas de la universidad. Esto ha llevado a la creación de una API dedicada, la cual facilitará la gestión de datos y la implementación de la lógica de negocio de la aplicación.


## Creación de imagen de docker 
```bash
    docker build -t escihu-virtual/api .
```

ejecutar la imagen

```bash
    docker run -d --rm -p 9090:90 -ti --name virtual-container escihu-virtual/api
```