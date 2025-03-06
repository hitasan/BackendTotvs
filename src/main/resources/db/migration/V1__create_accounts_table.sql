CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    dataemissao DATE NOT NULL,
    datavencimento DATE NOT NULL,
    datapagamento DATE,
    valor DOUBLE PRECISION NOT NULL,
    fornecedor VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    situacao VARCHAR(50) NOT NULL
);
