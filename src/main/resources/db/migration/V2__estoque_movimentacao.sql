-- V2__estoque_movimentacao.sql
-- Cria a tabela (sem FKs inline) e depois adiciona as FKs de forma condicional

create table if not exists mov_estoque (
                                           id           bigserial primary key,
                                           produto_id   uuid not null,
                                           deposito_id  uuid not null,
                                           tipo         varchar(10) not null, -- E/S, AJUSTE etc.
    quantidade   numeric not null,
    custo        numeric,
    origem       varchar(50),
    doc_ref      varchar(60),
    data         timestamp not null default now()
    );

-- Adiciona FK para produto(id) se ainda não existir
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM pg_constraint c
    JOIN pg_class t ON t.oid = c.conrelid
    WHERE c.conname = 'mov_estoque_produto_id_fkey'
      AND t.relname = 'mov_estoque'
  ) THEN
ALTER TABLE public.mov_estoque
    ADD CONSTRAINT mov_estoque_produto_id_fkey
        FOREIGN KEY (produto_id) REFERENCES public.produto(id);
END IF;
END$$;

-- Adiciona FK para deposito(id) se ainda não existir
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM pg_constraint c
    JOIN pg_class t ON t.oid = c.conrelid
    WHERE c.conname = 'mov_estoque_deposito_id_fkey'
      AND t.relname = 'mov_estoque'
  ) THEN
ALTER TABLE public.mov_estoque
    ADD CONSTRAINT mov_estoque_deposito_id_fkey
        FOREIGN KEY (deposito_id) REFERENCES public.deposito(id);
END IF;
END$$;

-- Índices para as FKs (idempotentes)
create index if not exists idx_mov_estoque_produto_id on public.mov_estoque(produto_id);
create index if not exists idx_mov_estoque_deposito_id on public.mov_estoque(deposito_id);
