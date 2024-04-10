function suma(numeros) {
    let sum = 0;
    for (let i = 0; i < numeros.length; i++) {
        sum += numeros[i];
        console.log(sum);
    }

    return sum;
}

suma([1, 2, 3, 4, 5])