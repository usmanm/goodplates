from django.db import models

# Create your models here.
class User(models.Model):
	username = models.CharField(max_length=100, unique=True, primary_key=True)
	def __unicode__(self):
		return self.username

class Venue(models.Model):
	locu_id = models.CharField(unique=True, max_length=200)
	name = models.CharField(max_length=255)
	def __unicode__(self):
		return self.name

class MenuItem(models.Model):
	locu_id = models.CharField(unique=True, max_length=200)
	venue = models.ForeignKey(Venue)
	title = models.CharField(max_length=255)
	description = models.CharField(max_length=2048, null=True, blank=True)
	section = models.CharField(max_length=255, null=True, blank=True)
	def __unicode__(self):
		return self.title

class Rating(models.Model):
	user = models.ForeignKey(User)
	menu_item = models.ForeignKey(MenuItem)
	LIKE = 1
	DISLIKE = 2
	VALUE_CHOICES = (
		(LIKE, 'Would eat again'),
		(DISLIKE, 'Would not eat again')
	)
	value = models.IntegerField(choices=VALUE_CHOICES)
