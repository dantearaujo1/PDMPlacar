# Trabalho 01 - Placar do Racha

Trabalho para o curso de Sistemas e Mídias Digitais, cadeira de Programação para Dispositivos Moveis, noturno no ano de 2022.
Aluno: Dante de Araújo Clementino

# Trabalho

O Trabalho consiste em apresentar um aplicativo funcional que permite persistir dados em uma sharedPreference e passá-los através das Activitys, será preciso ter uma RecicleView com um CardView para que possamos listar um histórico das partidas.

<h2 align="center"> Implementação do Icone </h2>
<h3 align="center"> Soccer Time </h3>
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172239866-60c666eb-22b0-4eab-8877-2977119a2172.png"  width=400/>
</p>

<h2 align="center"> Telas </h2>
<table>
  <tr>
    <td align="center">Main Activity</td>
     <td align="center">History Activity</td>
     <td align="center">Configuration Activity</td>
  </tr>
  <tr>
    <td align="center"><img src="https://user-images.githubusercontent.com/21104508/172241269-83a5efee-d2ab-45c5-b645-6bae5328f947.png"  width=100%/></td>
    <td align="center"><img src="https://user-images.githubusercontent.com/21104508/172241538-909072a0-31e9-4102-8185-4daa97c0c697.png"  width=100%/></td>
    <td align="center"><img src="https://user-images.githubusercontent.com/21104508/172242164-0bc1f7f3-2430-4aa1-815f-767fda2d5d55.png"  width=89%/></td>
  </tr>
  <tr>
    <td align="center" colspan=3>ScoreBoard Activity</td>
  </tr>
  <tr>
    <td align="center" colspan=3><img src="https://user-images.githubusercontent.com/21104508/172244523-a9c799aa-0ac1-4f0c-9951-bb598c1a2028.png"  width=100%/></td>    
  </tr>
 </table>
 
 
<h3 align="center"> CardView XML </h3>
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172245277-0ef49929-a856-4510-a21e-e8e8ffaf12ee.png"  width=400/>
</p>


<h2 align="center"> Implementação do Recycler View com o CardView </h2>
<p align="justify"> Foi criado uma classe do <b>Item View</b> para ser passado dentro de uma lista para o <b>Custom Adapter<b>.
  A classe Custom Adapter vai ser responsável por criar um <b>ViewHolder</b> e por fazer um bind da data da Lista da Itens (<b>ItemViewModel</b>) com as informações do View Holder.
Dentro do Custom Adapter foi implementado uma classe ViewHolder que deverá conter as informações que devem ser recebidas do Item View Model criado. 
</p>

  <h3> ItemViewModel </h3>
  
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172245717-88e142d1-e100-4a6e-b9ff-88fcda298595.png"  width=100%/>
</p>

  <h3> Custom Adapter </h3>
  
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172245790-ef4b02b8-a445-4bda-a02e-8b7aeadee3d8.png"  width=100%/>
</p>

  <h3> History Activity Logic </h3>
  
<p align="justify"> Dentro da Atividade History, foi criado uma função createHistory que se utilizará do arquivo config da sharedPreference contendo as informações das partidas salvas pelo aplicativo. É utilizado a variável MatchQuantity para determinar quantos jogos tem salvo e recuperar seus dados dinamicamente somando a string key desejada com o id da partida partindo do 0 até a quantidade de jogos
</p>
   
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172246046-665acfcc-3331-4d8a-a5ef-b8d1c9c60877.png"  width=100%/>
</p>

<h3> Configuration Activity Logic </h3>
  
<p align="justify">
Essa função salva as últimas configurações armazenadas nos editText para o jogador da casa e o jogador de fora nas variáveis LastPlayerHome, LastPlayerAway e LastCounterTime para podermos restaurar essas informações futuramente   
</p> 
  
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172252413-c33970c4-c1c9-4c51-b552-e2683ecd8e5b.png"  width=100%/>
</p>
  
<p align="justify">
Essa função é responsável por Armazenar as variáveis de cada partida a partir das variáveis matchId, armazenando em uma key + matchId  para dinamicamente criar jogos diferentes possíveis de serem restaurados a partir das sharedPreferences.
</p>
  
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172252491-e791b8b3-773b-4a48-a82a-e919ef065a12.png"  width=100%/>
</p>
  
<p align="justify">
Carrega os últimos valores armazenados dentro dos editTexts et_pHome e et_pAway assim como o último tempo escolhido.
</p>
  
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172252563-67cefd16-3a31-4e35-a753-badaa74744ee.png"  width=100%/>
</p>
  
<p align="justify">
Carrega a quantidade de jogos existentes na key MatchQuantity salva dentro dos sharedPreferences e seta o ID da partida para o id da ultima partida armazenada.
</p>
  
<p align="center">
<img src="https://user-images.githubusercontent.com/21104508/172252620-85d58404-09b3-470f-9bdd-7591e16446cd.png"  width=100%/>
</p>
  
<p align="justify">
Na mesma classe a função onCreate é responsável por criar o TimePickerDialog e mostrá-lo, assim como é responsável por setar os callbacks dos botões back e create. O botão create aumenta o valor de matchID em 1 e de matchQuantity em 1 antes de mais nada então cria uma intent passando os valores de cada editText da activity e da matchID e o tempo selecionado, em seguida chama createGame() e inicia a activity passando a intent. A função on create é responsável por chamar as funções loadLastConfig() e loadGames() ao fim de seu escopo.
</p>
  
  <h3> ScoreBoard Activity </h3>

<p align="justify">
Responsável por receber os extras que as outras atividades mandam pelo os intents e carregá-las nas lb_home (label Home), lb_away e na variável gameTime. isso é feito logo na função onCreate, assim como setar os ClickListeners nos botões. Utilizei uma lista de inteiros para fazer a lógica do undo e um Chronometer para realizar a logica do cronometro [Logica ta presente na função btn_startClock?.setOnClickListener e btn_2time.setOnCLickListener
</p>
  
  <h2> Utilização da API FUSEDLOCATION PROVIDER E DA API DO GOOGLE MAPS </h2>
  
  <h3> DetailHistoryActivity </h3>

<p align="justify">
Foi criada uma nova activity para apresentar os detalhes de cada partida, inicialmente foi mostrado o nome dos times nessa activity, seus pontos e o local onde ocorre a partida é mostrada em um mapa do google maps utilizando FragmentContainerView. É checado a permissão para a utilização da localização, tanto a coarse location permission quanto a Fine location. caso não tenha sido permitido , faz uma requisição das mesmas. Após isso utilizasse a função get location para receber a ultima localização conhecida do aparelho atravez de um fusedlocationproviderclient.
</p>
  
 <p align="center">
<img src="https://user-images.githubusercontent.com/21104508/175750791-0f0c1d35-2198-41be-a55d-10308ea4b812.png"  width=100%/>
</p>
   <p align="center">
<img src="https://user-images.githubusercontent.com/21104508/175750804-1a876335-331f-45f8-ac07-8ad13ce04c1f.png"  width=100%/>
</p>

 
