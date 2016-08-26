package tfg.repomap;

import tfg.repomap.mapping.entity2entity.Entity2Entity;
import tfg.repomap.scheme.entity.Entity;

public class Entity2EntityFactory {
	public static Entity2Entity newE2E() {
		Entity xmlElement = new Entity("atom");
		Entity owlClass = new Entity("Researcher");
		Entity2Entity e2e = new Entity2Entity(xmlElement, owlClass);
		return e2e;
	}
}
