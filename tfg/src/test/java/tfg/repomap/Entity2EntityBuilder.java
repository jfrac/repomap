package tfg.repomap;

import tfg.repomap.mapping.Entity2Entity;
import tfg.repomap.scheme.entity.Entity;

public class Entity2EntityBuilder {
	public static Entity2Entity newE2E() {
		Entity xmlElement = new Entity("complexContent");
		Entity owlClass = new Entity("Researcher");
		Entity2Entity e2e = new Entity2Entity(xmlElement, owlClass);
		return e2e;
	}
}
