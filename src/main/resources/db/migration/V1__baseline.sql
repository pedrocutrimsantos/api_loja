
create extension if not exists "pgcrypto";

create table if not exists categoria (
                                         id uuid primary key default gen_random_uuid(),
    nome varchar(120) not null unique,
    ativa boolean not null default true
    );

create table if not exists produto (
                                       id uuid primary key default gen_random_uuid(),
    sku varchar(60) not null unique,
    ean varchar(14),
    descricao varchar(200) not null,
    ncm varchar(8),
    unidade varchar(10),
    peso numeric,
    volume numeric,
    categoria_id uuid references categoria(id),
    marca varchar(120),
    ativo boolean not null default true
    );

create table if not exists deposito (
                                        id uuid primary key default gen_random_uuid(),
    nome varchar(120) not null unique
    );

create table if not exists posicao_estoque (
                                               id uuid primary key default gen_random_uuid(),
    produto_id uuid not null references produto(id),
    deposito_id uuid not null references deposito(id),
    qtd_disponivel numeric not null default 0,
    qtd_reservada numeric not null default 0,
    minimo numeric,
    maximo numeric,
    unique(produto_id, deposito_id)
    );

create table if not exists preco (
                                     id uuid primary key default gen_random_uuid(),
    produto_id uuid not null references produto(id),
    custo numeric,
    margem numeric,
    preco_vista numeric,
    preco_prazo numeric,
    inicio_vigencia timestamp,
    fim_vigencia timestamp
    );

insert into categoria(id, nome, ativa)
values (gen_random_uuid(), 'Geral', true)
    on conflict (nome) do nothing;

insert into deposito(id, nome)
values (gen_random_uuid(), 'Principal')
    on conflict (nome) do nothing;
