-- Crea la información de prueba para la base de datos

-- Borrar datos de prueba
DELETE FROM ARTICULO_DE_ROPA_ENTITY;
DELETE FROM PACK_DE_SERVICIOS_ENTITY_SERVICIOS;
DELETE FROM PACK_DE_SERVICIOS_ENTITY;
DELETE FROM TRABAJADOR_ENTITY_SERVICIOS;
DELETE FROM SERVICIO_ENTITY;
DELETE FROM SERVICIO_EXTRA_ENTITY;
DELETE FROM TRABAJADOR_ENTITY_SEDES;
DELETE FROM TRABAJADOR_ENTITY;
DELETE FROM SEDE_ENTITY;
DELETE FROM UBICACION_ENTITY;

-- Insertar datos de prueba

-- Ubicaciones
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (1, 4.603014, 74.065227, 'Bogotá', 'Cl. 20 #19a-71 a 19a-1');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (2, 6.225582, 75.549180, 'Medellín', 'Av. Principal #123');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (3, 3.451482, 76.568313, 'Cali', 'Cra. 50 #10-20, Cali');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (4, 10.977079, 74.804233, 'Barranquilla', 'Cl. 80 #5-10');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (5, 10.421440, 75.533275, 'Cartagena', 'Cra. 2 #15-30');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (6, 11.238928, 74.206239, 'Santa Marta', 'Av. Playa #8-12');

-- Sedes
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (1, 'Spa del Jardín', 'https://cdn.wallpapersafari.com/3/70/9hBAct.jpg', 1);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (2, 'Spa de la Montaña', 'https://www.visit.alsace/wp-content/uploads/lei/pictures/215002309-nature-spa-la-cheneaudiere-11-1600x900.jpg', 2);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (3, 'Spa del Mar', 'https://media.vogue.es/photos/5cc7143a860daa0dbfbbce99/master/w_1200,h_800,c_limit/los_mejores_spa_del_mundo_para_desconectar_en_navidad_546320723.jpg', 3);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (4, 'Spa de la Ciudad', 'https://elcomercio.pe/resizer/V2OjsMTPNqkYW_5Ps89shkhrFeA=/980x0/smart/filters:format(jpeg):quality(75)/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/SVUA6MLEDFDTHOTUQKUGQH2664.jpg', 4);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (5, 'Spa del Lago', 'https://as1.ftcdn.net/v2/jpg/02/10/55/00/1000_F_210550059_Iy7HSyMAPYYApFAW98rpmRveYhQKwXd4.jpg', 5);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (6, 'Spa del Bosque', 'https://foodandtravel.mx/wp-content/uploads/2020/01/SPAENELBOSQUE.jpg', 6);

-- Servicios
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (1, '3', 'Ser mayor de 16 años', true, 8, 'Masaje', 'Masaje de relajacion', '30000', 'https://i.pinimg.com/originals/5e/5c/bd/5e5cbd4d3517ba13db403b2145f91b07.jpg', 1);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (2, '4', 'Ser mayor de 18 años', true, 9, 'Facial', 'Tratamiento facial', '50000', 'https://img.freepik.com/foto-gratis/cosmetologa-aplicando-mascara-cara-cliente-salon-belleza_1303-16778.jpg', 1);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (3, '2', 'Ser mayor de 18 años', true, 10, 'Manicure', 'Servicio de manicure', '25000', 'https://img.freepik.com/free-photo/manicurist-master-makes-manicure-woman-s-hands-spa-treatment-concept_186202-7769.jpg', 2);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (4, '1', 'No aplican restricciones', true, 11, 'Depilacion', 'Depilacion de cejas', '15000', 'https://s3.amazonaws.com/arc-wordpress-client-uploads/infobae-wp/wp-content/uploads/2018/03/07125935/perfilado-.jpg', 2);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (5, '2', 'No aplican restricciones', true, 12, 'Corte de pelo', 'Corte de pelo unisex', '20000', 'https://img.freepik.com/fotos-premium/estilista-peluquero-masculino-corte-pelo-corte-pelo_488220-65360.jpg', 3);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (6, '1', 'Ser mayor de 16 años', true, 13, 'Tinte', 'Aplicación de tinte', '35000', 'https://media.glamour.mx/photos/61904d0a2d97bd4c522a1d2a/master/w_1600%2Cc_limit/267912.jpg', 3);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (7, '3', 'Ser mayor de 18 años', true, 14, 'Pedicure', 'Tratamiento de pedicure', '30000', 'https://img.freepik.com/free-photo/pedicurist-master-makes-pedicure-woman-s-legs-spa-treatment-concept_186202-7773.jpg', 4);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (8, '2', 'No aplican restricciones', true, 15, 'Maquillaje', 'Maquillaje profesional', '40000', 'https://img.freepik.com/foto-gratis/artista-maquillaje-cerca-aplicar-sombra-ojos-mujer-pincel_23-2148332527.jpg', 4);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (9, '1', 'Ser mayor de 16 años', true, 16, 'Peinado', 'Peinado de eventos', '25000', 'https://img.freepik.com/fotos-premium/peluqueria-mujer-peinado-salon-belleza_1122-6538.jpg', 5);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (10, '2', 'Ser mayor de 18 años', true, 17, 'Uñas acrílicas', 'Aplicación de uñas acrílicas', '45000', 'https://e1.pxfuel.com/desktop-wallpaper/921/752/desktop-wallpaper-nails-acrylic-nail.jpg', 5);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (11, '4', 'No aplican restricciones', true, 18, 'Masaje deportivo', 'Masaje para deportistas', '35000', 'https://masajesbogota.com/wp-content/uploads/2020/07/Masaje-Deportivo-1.jpg', 6);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (12, '2', 'Ser mayor de 16 años', true, 19, 'Tratamiento capilar', 'Tratamiento para el cabello', '50000', 'https://w0.peakpx.com/wallpaper/94/804/HD-wallpaper-chemical-peels-anton-s-salon-hair-treatment.jpg', 6);

-- Create pack 1
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (1, 20, 'Ritual de relajación profunda', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 1);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (1, 1);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (1, 2);

-- Create pack 2
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (2, 15, 'Pack de belleza completa', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 2);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (2, 3);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (2, 4);

-- Create pack 3
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (3, 10, 'Pack de cuidado personal', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 3);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (3, 5);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (3, 6);

-- Create pack 4
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (4, 25, 'Pack de relajación y bienestar', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 4);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (4, 7);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (4, 8);

-- Create pack 5
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (5, 30, 'Pack de belleza y estilo', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 5);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (5, 9);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (5, 10);

-- Create pack 6
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (6, 12, 'Pack de tratamientos especiales', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 6);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (6, 11);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (6, 12);

-- Articulos
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (1, 'Crop Top The Spa', 'Camiseta corta', 15000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 100, 6, 'azul', 1);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (2, 'Pantalón Deportivo', 'Pantalón cómodo para entrenamiento', 25000.0, 'https://http2.mlstatic.com/D_NQ_NP_897854-MLM52472394532_112022-W.jpg', 50, 8, 'negro', 1);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (3, 'Vestido Elegante', 'Vestido para ocasiones especiales', 35000.0, 'https://images.unsplash.com/photo-1612336307429-8a898d10e223?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Z2lybCUyMGRyZXNzfGVufDB8fDB8fHww&w=1000&q=80', 20, 10, 'rojo', 2);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (4, 'Camisa Casual', 'Camisa cómoda para uso diario', 20000.0, 'https://img.freepik.com/foto-gratis/retrato-sonriente-feliz-guapo-camisa-casual-azul-aislado-sobre-fondo-blanco_186202-4729.jpg', 30, 7, 'blanco', 2);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (5, 'Falda Plisada', 'Falda elegante y versátil', 18000.0, 'https://preview.free3d.com/img/2019/11/2201654115522577639/ymff1d4y.jpg', 40, 9, 'negro', 3);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (6, 'Short Deportivo', 'Short cómodo para actividades físicas', 12000.0, 'https://ae01.alicdn.com/kf/S6cf475b22fbc40948a2b983585e8fc157/Pantalones-cortos-de-cintura-alta-sin-costuras-para-mujer-Shorts-deportivos-de-realce-para-Fitness-ciclismo.jpg', 60, 5, 'gris', 3);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (7, 'Camiseta Estampada', 'Camiseta con diseño exclusivo', 25000.0, 'https://http2.mlstatic.com/D_NQ_NP_684351-MCO31544198213_072019-O.jpg', 80, 7, 'azul', 4);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (8, 'Pantalón Casual', 'Pantalón cómodo para uso diario', 30000.0, 'https://d3ugyf2ht6aenh.cloudfront.net/stores/188/770/products/pantalon-trench-cargo-negro_kim-31-2e5dc396e638f1263016832076933833-640-0.webp', 25, 6, 'negro', 4);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (9, 'Blusa Elegante', 'Blusa con estilo sofisticado', 28000.0, 'https://static6.depositphotos.com/1000660/628/i/600/depositphotos_6282272-stock-photo-elegant-blouse-on-a-white.jpg', 35, 8, 'blanco', 5);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (10, 'Short Vaquero', 'Short de tela vaquera resistente', 22000.0, 'https://cdn.create.vista.com/api/media/small/167105544/stock-photo-jeans-shorts-isolated', 15, 7, 'azul', 5);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (11, 'Camisa Formal', 'Camisa ideal para eventos elegantes', 40000.0, 'https://img.freepik.com/psd-premium/camisa-vestir-mujer-mockup_184826-478.jpg', 50, 9, 'blanco', 6);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (12, 'Pantalón de Vestir', 'Pantalón formal para ocasiones especiales', 35000.0, 'https://cdn.fashiola.es/L609149483/120-lino-mujer-pantalones-pantalones.jpg', 40, 8, 'negro', 6);

-- Servicios Extra
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (1, 'Sandwich Gourmet', 'Para disfrutar mientras espera ser atendido', 5000.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 1);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (2, 'Bebida Refrescante', 'Una bebida refrescante para acompañar su visita', 3000.0, 'https://www.gastrolabweb.com/u/fotografias/m/2021/5/2/f850x638-12606_90095_5050.jpg', true, 1);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (3, 'Café Especial', 'Un café especial hecho por nuestros baristas', 4000.0, 'https://ecocosas.com/wp-content/uploads/2021/12/mejor-cafe-1024x573.jpg', true, 2);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (4, 'Postre Delicioso', 'Un postre delicioso para culminar su experiencia', 6000.0, 'https://media.istockphoto.com/id/182026106/es/foto/deliciosos-panna-cotta-con-frutos.jpg?s=612x612&w=0&k=20&c=wdRLl5PgHW1jlqY9HvlP1ewcbmRYsvJuuCuLA_lRKRE=', true, 2);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (5, 'Aperitivo Exclusivo', 'Un aperitivo exclusivo para abrir su apetito', 4500.0, 'https://w0.peakpx.com/wallpaper/863/249/HD-wallpaper-red-caviar-appetizer-salmon-caviar-red-caviar-on-a-spoon-caviar.jpg', true, 3);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (6, 'Refresco Natural', 'Un refresco natural hecho con ingredientes frescos', 3500.0, 'https://hd-cosmetics.com/wp-content/uploads/2020/11/fresh-juice.jpg', true, 3);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (7, 'Snack Saludable', 'Un snack saludable para picar entre servicios', 2500.0, 'https://hdstatic.net/gridfs/holadoctor/553ea3e0b93795495b8b4567_2_7-143016868662.jpg', true, 4);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (8, 'Té Caliente', 'Un té caliente para relajarse durante su visita', 3500.0, 'https://media.istockphoto.com/id/912302488/es/foto/té-recién-preparado-en-un-vaso-de-vidrio-y-algunas-hierbas-sobre-un-fondo-oscuro-de-madera.jpg?s=612x612&w=0&k=20&c=cPgTCsp-eGQbsba0s--MP3BHsDNHinTNG4rMtdwF_gM=', true, 4);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (9, 'Batido Energizante', 'Un batido energizante para revitalizarse', 4500.0, 'https://www.lamansiondelasideas.com/wp-content/uploads/2022/06/Batido-despues-de-entrenar.jpg', true, 5);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (10, 'Galleta Casera', 'Una galleta casera para endulzar su día', 2000.0, 'https://s2.abcstatics.com/media/gurmesevilla/2014/04/receta-galletas-caseras.jpg', true, 5);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (11, 'Zumo Natural', 'Un zumo natural hecho con frutas frescas', 4000.0, 'https://cdn01.segre.com/uploads/imagenes/bajacalidad/2023/03/13/_mateuszfeliksiktmogarnogfsunsplash_62facc09.jpg?739a1af7f4773e3f997d76ef8c1a977e', true, 6);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (12, 'Helado Artesanal', 'Un helado artesanal para refrescar su paladar', 5000.0, 'https://images7.alphacoders.com/391/391001.jpg', true, 6);

-- Trabajadores

-- Trabajador 1 
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (1, 'https://www.leisureopportunities.co.uk/images/464391_26750.jpg', 'Sophia Anderson', 10, true);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (1, 1);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (1, 1), (1, 2);

-- Trabajador 2
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (2, 'https://media.istockphoto.com/id/915704700/photo/whatever-pain-you-have-we-will-sort-it-out.jpg?s=612x612&w=0&k=20&c=K2q0npEXddIThgfKp5GvMVkjGGa_0JuuS8wjICfS2K0=', 'John Smith', 8, false);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (2, 2), (2, 3);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (2, 3), (2, 4), (2, 5);

-- Trabajador 3
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (3, 'https://media.istockphoto.com/id/486842478/photo/woman-masseuse-at-the-spa.jpg?s=612x612&w=0&k=20&c=wy_t6tFdPp18fNhYdWJQa8xv4cdL0H_n-zdhOgJvC8o=', 'Emily Johnson', 9, true);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (3, 4);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (3, 6), (3, 7);

-- Trabajador 4
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (4, 'https://img.freepik.com/fotos-premium/retrato-empleado-fisioterapeuta-spa-quiropractico-listo-fisioterapia-consulta-feliz-fisioterapia-trabajador-apoyo-que-siente-orgulloso-exito-clinica-terapia-salud_590464-104454.jpg?w=740', 'Michael Williams', 7, false);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (4, 5), (4, 6);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (4, 8), (4, 9), (4, 10);

-- Trabajador 5
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (5, 'https://media.istockphoto.com/id/1126310650/photo/portrait-of-a-business-owner-working-at-a-spa.jpg?s=612x612&w=0&k=20&c=n2hFdeF9pWlB6ESZdAdXop0JYnHRf5L99PzvyYD331Y=', 'Olivia Davis', 9, true);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (5, 1), (5, 2);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (5, 11), (5, 12);

-- Trabajador 6
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (6, 'https://img.wellspa360.com/files/base/allured/all/image/2022/05/dreamstime_s_241404666.62882510b027a.png', 'James Brown', 8, false);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (6, 3);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (6, 1), (6, 2), (6, 3), (6, 4);
