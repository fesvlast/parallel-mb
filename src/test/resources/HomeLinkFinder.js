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

    function isIgnoredLink(element){
        if((window.location.origin + '/#') == element.href){
            return true
        }
        if(element.href.includes('facebook')){
            return true
        }
        if(element.href.includes('#')){
            return true
        }
        return false;
    }

    elements.forEach(function(item) {
      let currentHost = window.location.hostname;

      if(!isHidden(item) && !isIgnoredLink(item)){
            if(item.href.includes(currentHost) ){
               console.log(isIgnoredLink(item));
               currentLinks.push(item);
            }else{
                foreignLinks.push(item);
            }
       }
     });
    console.log('Total: '+elements.length);
    console.log('Current: '+currentLinks.length);
    console.log('Foreign: '+foreignLinks.length);

     for(let i=0; i< 3; i++){
        try {
             let num = randomIntFromInterval(0, currentLinks.length);
             console.log(num);
             console.log(currentLinks[num]);
             currentLinks[num].scrollIntoView();
             setTimeout(()=>{
                     currentLinks[num].click();
                   }, 1000);
             return currentLinks[num].href;
        }
        catch(err) {}
     }
    return null;
}
let urlClicked = testScript();

return urlClicked;






