function isHidden(el) {
    var style = window.getComputedStyle(el);
    return (style.display === 'none') && (style.visibility === 'visible');
}

function randomIntFromInterval(min,max)
{
    return Math.floor(Math.random()*(max-min+1)+min);
}

let elements = Array.from(document.getElementsByTagName('a'));

let currentLinks = [];
let foreignLinks = []
let exceptions = ['#','/'];
console.log();
function isNotIgnoredLink(element){
    return (window.location.origin + '/#') != element.href;
}

elements.forEach(function(item) {
  let currentHost = window.location.hostname;

  if(!isHidden(item) && isNotIgnoredLink(item)){
        if(item.href.includes(currentHost) ){
           console.log(isNotIgnoredLink(item));
           currentLinks.push(item);
        }else{
            foreignLinks.push(item);
        }
   }
 });
console.log('Total: '+elements.length);
console.log('Current: '+currentLinks.length);
console.log('Foreign: '+foreignLinks.length);
 let num = randomIntFromInterval(0, currentLinks.length);
 console.log(num);
 console.log(currentLinks[num]);
 currentLinks[num].scrollIntoView();

  setTimeout(()=>{
    currentLinks[num].click();
  }, 10000);






