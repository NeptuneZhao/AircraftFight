package com.halfcooler.tests;

import com.halfcooler.flying.prop.Prop;
import com.halfcooler.flying.prop.PropBullet;
import com.halfcooler.flying.warplane.WarplaneHero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WarplaneHeroTest
{

	@BeforeEach
	void setUp()
	{
	}

	@AfterEach
	void tearDown()
	{
	}

	@Test
	void getHealth()
	{
		WarplaneHero hero = WarplaneHero.Instance;
		assertEquals(900, hero.GetHealth());
	}

	@Test
	void isCrash()
	{
		WarplaneHero hero = WarplaneHero.Instance;
		Prop prop = new PropBullet(hero);
		assertTrue(hero.IsCrash(prop));
	}

	@Test
	void changeHealth()
	{
		WarplaneHero hero = WarplaneHero.Instance;
		hero.ChangeHealth(-200);
		assertEquals(800, hero.GetHealth());
		hero.ChangeHealth(100);
		assertEquals(900, hero.GetHealth());
	}
}