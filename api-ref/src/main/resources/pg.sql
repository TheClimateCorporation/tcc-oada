CREATE DATABASE oada;

# Enable PostGIS extensions.
CREATE EXTENSION postgis;

CREATE TABLE landunit
(
  id bigserial NOT NULL,
  user_id bigint NOT NULL,
  name text NOT NULL,
  farm_name text,
  client_name text,
  acres float NOT NULL,
  source text,
  other_props json,
  geom geometry(MultiPolygon,4326),
  CONSTRAINT landunit_pkey PRIMARY KEY (id)
);

CREATE INDEX lu_geo_idx_1 ON landunit USING gist (geom);

CREATE INDEX lu_user_id_idx_1 ON landunit (user_id);
