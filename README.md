# microservice_java
microservice_java

El sistema a desarrollar está planteado en el contexto del negocio bancario que a medida que se va avanzando en los proyectos, se irá ampliando en base a este mismo proyecto.

## Modelo de arquitectura (Model C4)

### Nivel 1 - Diagrama de contexto del sistema

![Nivel 1 - Diagrama de contexto del sistema](/img/modelc4_1.svg)

### Nivel 2 - Diagrama del contenedor

![Nivel 2 - Diagrama del contenedor](/img/modelc4_2.svg)

### Nivel 3 - Diagrama de componentes

![Nivel 3 - Diagrama de componentes](/img/modelc4_3.svg)


## Installation

1) Desplegar config server: Agregar en config-server/src/main/resources/application.yml su respectivo ***username***  y ***password*** ([Github-tokens](https://github.com/settings/tokens)).
    - La ruta para los archivos de configuración es [Config-server](https://github.com/iminayad/microservice_java.git).


3) Desplegar eureka server: Para confirmar despliegue consulte la ruta:

    ```sh
    http://localhost:8761/
    ```
    Hay dos perfiles actualmente dev(ambiente local) y prod(ambiente dockerizado):
   - Para el ambiente dev los yml de los microservicios deben apuntar a:

        ```sh
        import: "optional:configserver:http://localhost:8888/"
        ```
    - Para el ambiente prod los yml de los microservicios deben apuntar a:
    
        ```sh
        import: "optional:configserver:http://config-server:8888/"
        ```
4) Desplegar gateway.