## Desafio (URI 2700)
Uma politica de prestígio visando a presidência no próximo ano está planejando um evento para angariar fundos para sua campanha. Ela possui uma lista de pessoas abastadas no país e quer convidá-los de uma forma a maximizar seus fundos.

Algumas vezes os ricos e abastados tem comportamentos fúteis e não gostam da ideia de alguém mais rico ou bonito do que eles existir. Toda vez que alguém assim encontra uma pessoa rigorosamente mais bonita, mas não rigorosamente mais rica, então uma discussão começa. Similarmente, se eles encontram uma pessoa que é rigorosamente mais rica mas não rigorosamente mais bonita uma discussão também começa. Essas duas situações são as únicas causas possíveis de discussões entre dois indivíduos. Assim, duas pessoas nunca discutirão caso uma seja estritamente mais bonita e mais rica que a outra. Também não ocorrem discussões quando ambas as pessoas são igualmente ricas e igualmente bonitas.

Como a nossa presidenciável gostaria de garantir o máximo de dinheiro possível, discussões devem ser evitadas a qualquer custo, pois poderiam arruinar a campanha ou o evento. Dado as características de algumas pessoas abastadas no país, você deve encontrar uma lista de convidados que maximize as doações enquanto garanta que nenhuma discussão ocorra no evento.

## Entrada
A primeira linha contem um inteiro N (1 ≤ N ≤ 105 ) representando o número possível de convidados. Cada uma das próximas N linhas descreve um possível candidato com três inteiros B, F e D (1 ≤ B, F, D ≤ 109 ), indicando respectivamente seu nivel de beleza, sua fortuna e quanto esta pessoa doaria caso fosse convidada.

## Saída
Imprima uma única linha contendo um inteiro que indica a soma máxima de doações possíveis para uma lista de convidados que não gere discussão alguma durante o evento.

|Exemplo de Entrada|Exemplo de Saída|
|:-------------|:-------------|
|4|60|
|1 2 50||
|2 1 50||
|2 2 30||
|1 1 30||

|Exemplo de Entrada|Exemplo de Saída|
|:-------------|:-------------|
|3|9|
|3 3 3||
|5 5 3||
|2 2 3||

|Exemplo de Entrada|Exemplo de Saída|
|:-------------|:-------------|
|3|25|
|2 8 13||
|1 4 12||
|2 1 16||
