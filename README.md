Esse repositório é divido em duas seções `java-version` e `kotlin-version`, ambas possuem a mesma infraestrutura de código
com o objetivo de implementar diferentes tipos de autenticação e autorização de um usuário com o **Spring Security**.

Grande parte da construção desses códigos foram provenientes sobre o que eu entendi da [documentação oficial do Spring
Security](https://docs.spring.io/spring-security/reference/index.html). Caso haja
algum erro conceitual ou alguma sugestão de implementação diferente, fique livre para abrir uma **Issue** e contribuir.

# Entendendo o core do Spring Security

Quando você adiciona o Spring Security como dependência do seu projeto, ele automaticamente cria uma **`SecurityFilterChain`**
contendo um `formLogin()` configurado como forma de autenticação padrão. O filtro que é utilizado para processar sua autenticação
através do `formLogin()` é o [UsernamePasswordAuthenticationFilter](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html#servlet-authentication-form).
Esse filtro produz um `UsernamePasswordAuthenticationToken` o qual representa uma "intenção" de autenticação. Irei comentar
melhor sobre ele em algum momento mais para frente. A ordem que esses filtros são aplicados importa e você pode consultar todos 
os filtros que o Spring Security possui [aqui](https://github.com/spring-projects/spring-security/blob/7.0.2/config/src/main/java/org/springframework/security/config/annotation/web/builders/FilterOrderRegistration.java)

![img.png](docs/img.png)

Quando uma requisição HTTP chega à aplicação, o `DelegatingFilterProxy` delega o trabalho para o `FilterChainProxy`. 
Este, por sua vez, identifica qual `SecurityFilterChain` é a mais adequada para processar aquela requisição específica, 
garantindo que as políticas de segurança sejam aplicadas antes de chegar aos Controllers.

Como o `formLogin()` é mais indicado caso sua aplicação utilize um front-end, estarei alterando esse filtro para o `httpBasic()`,
o qual também produz um `UsernamePasswordAuthenticationToken`. Esse `UsernamePasswordAuthenticationToken` contém as credenciais
que o usuário passou na requisição e esse objeto será enviado para um `AuthenticationManager` que vai ser responsável por 
autenticar o usuário ou não.

O `AuthenticationManager` é uma interface e sua implementação mais comum é o `ProviderManager`, o `ProviderManager` é responsável
por pegar o `UsernamePasswordAuthenticationToken` gerado e repassá-lo para algum `AuthenticationProvider` para executar a autenticação.

O [ProviderManager](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/authentication/ProviderManager.html)
contém uma lista de diversos `AuthenticationProvider`'s que são responsáveis por lidarem com diversos tipos de autenticações diferentes.
Ele irá procurar qual `AuthenticationProvider` é o ideal para lidar com aquele tipo de autenticação.

No caso, o Provider o qual será acionado para realizar a autenticação do usuário é o [DaoAuthenticationProvider](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html),
dentro dele contém um [UserDetailsService](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/core/userdetails/UserDetailsService.html)
que é responsável por consultar um banco de dados, seja em memória ou não, e transformar um o resultado da consulta em um
objeto do tipo [UserDetails](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/core/userdetails/UserDetails.html).

O que irei fazer é construir um `UserDetailsService` que tenha acesso ao meu banco de dados e fazer o `DaoAuthenticationProvider` utilizar
esse meu `UserDetailsService`, para isso criei as seguintes classes:

- `APIController`: Possui apenas um endpoint público que indica se a API está ativa ou não
- `BasiAuthController`: É o controller que terá o endpoint responsável para autenticar o usuário
- `SampleUserController`: É o controller utilizado para criar novos usuários na base de dados