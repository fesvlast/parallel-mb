let testScript = ()=>{

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
function isIgnoredLink(element){
    return (window.location.origin + '/#') == element.href;
}

elements.forEach(function(item) {
  let currentHost = window.location.hostname;

  if(!isHidden(item) && !isIgnoredLink(item)){
        if(item.href.includes(currentHost) ){
           console.log(isIgnoredLink(item));
           currentLinks.push(item);
        }else{
            console.log(item.href);
            foreignLinks.push(item);
        }
   }
 });
console.log('Total: '+elements.length);
console.log('Current: '+currentLinks.length);
console.log('Foreign: '+foreignLinks.length);

 let num = randomIntFromInterval(0, foreignLinks.length);

 foreignLinks[num].scrollIntoView();

setTimeout(()=>{ foreignLinks[num].click(); return foreignLinks[num].href}, 1000);
     return foreignLinks[num].href;
}

let urlClickedForeign = testScript();

return urlClickedForeign;










