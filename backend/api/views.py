# Create your views here.
from django.http import HttpResponse, HttpResponseBadRequest
from api.models import User, MenuItem, Rating

import json

def error_json(message):
	return json.dumps({"error": message})

def user_json(registered):
	return json.dumps({"registered": registered}, indent=4)
def user(request):
	try:
		username = request.GET["username"]
		user = User.objects.get(username=username)
		return HttpResponse(user_json(registered=True))
	except User.DoesNotExist:
		return HttpResponse(user_json(registered=False))
	except KeyError:
		return HttpResponseBadRequest("username required but not provided")

def register_user(request):
	try:
		username = request.GET["username"]
	except KeyError:
		return HttpResponseBadRequest("username required but not provided")
	u = User(username=username)
	u.save()
	return HttpResponse(user_json(registered=True))

def rate(request):
	try:
		menu_item_id = request.GET["item"]
	except KeyError:
		return HttpResponseBadRequest(error_json('item required but not prodided'))
	try:
		username = request.GET["username"]
	except KeyError:
		return HttpResponseBadRequest(error_json('username required but not prodided'))
	try:
		rating = request.GET["rating"]
	except KeyError:
		return HttpResponseBadRequest(error_json('rating required but not prodided'))
	try:
		item = MenuItem.objects.get(locu_id=menu_item_id)
		user = User.objects.get(username=username)
	except MenuItem.DoesNotExist:
		return HttpResponseBadRequest(error_json('Menu item "%s" does not exist'%menu_item_id))
	except User.DoesNotExist:
		return HttpResponseBadRequest(error_json('User "%s" does not exist'%username))
	if rating.lower() == "like":
		r = Rating(user=user, menu_item=item, value=Rating.LIKE)
		r.save()
		return HttpResponse("")
	elif rating.lower() == "dislike":
		r = Rating(user=user, menu_item=item, value=Rating.DISLIKE)
		r.save()
		return HttpResponse("")
	else:
		return HttpResponseBadRequest(error_json('Rating "%s" is not valid'%rating))

def get_ranked_items(request):
	try:
		username = request.GET["username"]
	except KeyError:
		return HttpResponseBadRequest(error_json("username required but not provided"))
	try:
		user = User.objects.get(username=username)
	except User.DoesNotExist:
		return HttpResponseBadRequest(error_json('user "%s" not found in database'%username))
	page = int(request.GET.get("page", 1))
	count = 50
	items = MenuItem.objects.all()
	from random import shuffle
	shuffle(items)
	output = [ {"locu_id": i.locu_id,
	            "title": i.title,
	            "venue_name": i.venue.name,
	            "description": i.description,
	            "section": i.section}
	           for i in items[:count] ]
	return HttpResponse(json.dumps(output, indent=4))
