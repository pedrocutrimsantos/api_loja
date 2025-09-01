-- V3__vendas_pedidos.sql

-- 1) pedidos
create table if not exists pedido_venda (
                                            id bigserial primary key,
                                            data timestamp not null default now(),
    total numeric not null default 0
    );

-- 2) itens (produto_id é UUID e FKs inline)
create table if not exists item_pedido (
                                           id bigserial primary key,
                                           pedido_id bigint not null references public.pedido_venda(id) on delete cascade,
    produto_id uuid  not null references public.produto(id),
    quantidade numeric not null,
    preco_unitario numeric not null,
    desconto numeric default 0,
    total_item numeric not null
    );

-- 3) índices
create index if not exists idx_item_pedido_pedido_id  on public.item_pedido(pedido_id);
create index if not exists idx_item_pedido_produto_id on public.item_pedido(produto_id);
