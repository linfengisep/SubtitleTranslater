/*créer un table pour stocker identité de la scène et le temps associé;*/
CREATE TABLE TimeId (
	sceneId INT NOT NULL,
	timeLine VARCHAR(255) NOT NULL,
	PRIMARY KEY(sceneId)
);
/*créer un table pour stocker les sous-titres*/
CREATE TABLE Subtitles (
	id INT NOT NULL AUTO_INCREMENT,
	subtitle VARCHAR(255) NOT NULL,
	subtitleTranslated VARCHAR(255),
	sceneId INT NOT NULL,
	PRIMARY KEY(id),
    FOREIGN KEY (sceneId) REFERENCES TimeId(sceneId)
);

