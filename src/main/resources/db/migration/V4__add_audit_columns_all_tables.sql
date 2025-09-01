
ALTER TABLE categoria
    ADD COLUMN IF NOT EXISTS data_inclusao     timestamp(6) NOT NULL DEFAULT now(),
    ADD COLUMN IF NOT EXISTS data_alteracao    timestamp(6),
    ADD COLUMN IF NOT EXISTS data_exclusao     timestamp(6),
    ADD COLUMN IF NOT EXISTS usuario_inclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_alteracao varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_exclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS ativa             boolean NOT NULL DEFAULT true,
    ADD COLUMN IF NOT EXISTS excluido          boolean NOT NULL DEFAULT false;

ALTER TABLE produto
    ADD COLUMN IF NOT EXISTS data_inclusao     timestamp(6) NOT NULL DEFAULT now(),
    ADD COLUMN IF NOT EXISTS data_alteracao    timestamp(6),
    ADD COLUMN IF NOT EXISTS data_exclusao     timestamp(6),
    ADD COLUMN IF NOT EXISTS usuario_inclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_alteracao varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_exclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS ativa             boolean NOT NULL DEFAULT true,
    ADD COLUMN IF NOT EXISTS excluido          boolean NOT NULL DEFAULT false;


ALTER TABLE deposito
    ADD COLUMN IF NOT EXISTS data_inclusao     timestamp(6) NOT NULL DEFAULT now(),
    ADD COLUMN IF NOT EXISTS data_alteracao    timestamp(6),
    ADD COLUMN IF NOT EXISTS data_exclusao     timestamp(6),
    ADD COLUMN IF NOT EXISTS usuario_inclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_alteracao varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_exclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS ativa             boolean NOT NULL DEFAULT true,
    ADD COLUMN IF NOT EXISTS excluido          boolean NOT NULL DEFAULT false;


ALTER TABLE item_pedido
    ADD COLUMN IF NOT EXISTS data_inclusao     timestamp(6) NOT NULL DEFAULT now(),
    ADD COLUMN IF NOT EXISTS data_alteracao    timestamp(6),
    ADD COLUMN IF NOT EXISTS data_exclusao     timestamp(6),
    ADD COLUMN IF NOT EXISTS usuario_inclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_alteracao varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_exclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS ativa             boolean NOT NULL DEFAULT true,
    ADD COLUMN IF NOT EXISTS excluido          boolean NOT NULL DEFAULT false;


ALTER TABLE mov_estoque
    ADD COLUMN IF NOT EXISTS data_inclusao     timestamp(6) NOT NULL DEFAULT now(),
    ADD COLUMN IF NOT EXISTS data_alteracao    timestamp(6),
    ADD COLUMN IF NOT EXISTS data_exclusao     timestamp(6),
    ADD COLUMN IF NOT EXISTS usuario_inclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_alteracao varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_exclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS ativa             boolean NOT NULL DEFAULT true,
    ADD COLUMN IF NOT EXISTS excluido          boolean NOT NULL DEFAULT false;


ALTER TABLE pedido_venda
    ADD COLUMN IF NOT EXISTS data_inclusao     timestamp(6) NOT NULL DEFAULT now(),
    ADD COLUMN IF NOT EXISTS data_alteracao    timestamp(6),
    ADD COLUMN IF NOT EXISTS data_exclusao     timestamp(6),
    ADD COLUMN IF NOT EXISTS usuario_inclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_alteracao varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_exclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS ativa             boolean NOT NULL DEFAULT true,
    ADD COLUMN IF NOT EXISTS excluido          boolean NOT NULL DEFAULT false;


ALTER TABLE posicao_estoque
    ADD COLUMN IF NOT EXISTS data_inclusao     timestamp(6) NOT NULL DEFAULT now(),
    ADD COLUMN IF NOT EXISTS data_alteracao    timestamp(6),
    ADD COLUMN IF NOT EXISTS data_exclusao     timestamp(6),
    ADD COLUMN IF NOT EXISTS usuario_inclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_alteracao varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_exclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS ativa             boolean NOT NULL DEFAULT true,
    ADD COLUMN IF NOT EXISTS excluido          boolean NOT NULL DEFAULT false;


ALTER TABLE preco
    ADD COLUMN IF NOT EXISTS data_inclusao     timestamp(6) NOT NULL DEFAULT now(),
    ADD COLUMN IF NOT EXISTS data_alteracao    timestamp(6),
    ADD COLUMN IF NOT EXISTS data_exclusao     timestamp(6),
    ADD COLUMN IF NOT EXISTS usuario_inclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_alteracao varchar(255),
    ADD COLUMN IF NOT EXISTS usuario_exclusao  varchar(255),
    ADD COLUMN IF NOT EXISTS ativa             boolean NOT NULL DEFAULT true,
    ADD COLUMN IF NOT EXISTS excluido          boolean NOT NULL DEFAULT false;




