from django.db import models

# Create your models here.
class User(models.Model):
	username = models.CharField(max_length=100, unique=True, primary_key=True)

class Venue(models.Model):
	id = models.IntegerField(unique=True, primary_key=True)
	name = models.CharField(max_length=255)

class MenuItem(models.Model):
	id = models.IntegerField(unique=True, primary_key=True)
	venue = models.ForeignKey(Venue)
	name = models.CharField(max_length=255)

class Preference(models.Model):
	user = models.ForeignKey(User)
	item = models.ForeignKey(MenuItem)
	LIKE = 1
	DISLIKE = 2
	VALUE_CHOICES = (
		(LIKE, 'Would eat again'),
		(DISLIKE, 'Would not eat again')
	)
	value = models.IntegerField(choices=VALUE_CHOICES)
